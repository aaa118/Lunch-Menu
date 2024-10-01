package com.lunchmenu.home.data.repository

import android.util.Log
import com.lunchmenu.home.data.datasource.local.AppDatabase
import com.lunchmenu.home.data.datasource.local.LunchMenuDataSource
import com.lunchmenu.home.data.datasource.local.dao.BusinessesDao
import com.lunchmenu.home.data.datasource.local.dao.LunchMenuDao
import com.lunchmenu.home.data.datasource.local.entity.LunchMenuItem
import com.lunchmenu.home.data.datasource.remote.YelpApi
import com.lunchmenu.home.data.datasource.remote.model.YelpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import javax.inject.Inject

private const val TAG = "HomeViewModel"

class LunchMenuRepository @Inject constructor(
    private val lunchMenuDataSource: LunchMenuDataSource,
    private val yelpApi: YelpApi,
    private val appDatabase: AppDatabase,
) {
    private val businessesDao: BusinessesDao
        get() = appDatabase.businessesDao()

    private val lunchMenuDao: LunchMenuDao
        get() = appDatabase.lunchMenuDao()

    // Define a CoroutineScope for the repository
    var job = SupervisorJob()
    private val repositoryRemoteScope = CoroutineScope(job + Dispatchers.IO)

    /**
     * Can be used to have remove data source to fetch from server for updating local data.
     */
    suspend fun getLunchMenu(): Result<Flow<List<LunchMenuItem>>> = withContext(Dispatchers.IO) {
        try {
            // Attempt to fetch the lunch menu
            val cacheMenus = lunchMenuDao.getAllFlowable()
            val deferredList = mutableListOf<Deferred<Int>>()
            val jobList = mutableListOf<Job>()
            // Fetch From remote
            Log.i(TAG, "getLunchMenu: Start Async")
            repeat(2) {

                val job = repositoryRemoteScope.async {
                    getYelpBeerBusinesses()
                    Log.i(TAG, "loop complete ")
                }
                val job2 = repositoryRemoteScope.async {
                    getYelpPizzaBusinesses()
                    Log.i(TAG, "loop complete ")
                }
                val job3 = repositoryRemoteScope.async {
                    fetchFromRemote()
                    Log.i(TAG, "loop complete ")
                }
                jobList.add(job)
                jobList.add(job2)
                jobList.add(job3)
            }
//            deferredList.awaitAll()
            jobList.joinAll()
            Log.i(TAG, "getLunchMenu: Completed")


            return@withContext Result.success(cacheMenus)
        } catch (e: Exception) {
            // Handle potential exceptions during the fetch
            return@withContext Result.failure(e)
        }
    }

    suspend fun getLunchMenu2(): Result<Flow<List<LunchMenuItem>>> = supervisorScope {
        try {
            val cacheMenus = lunchMenuDao.getAllFlowable()
            val deferredList = mutableListOf<Deferred<Int>>()
            // Fetch From remote
            Log.i(TAG, "getLunchMenu: Start Async")
            repeat(2) {
                deferredList.add(async {
                    getYelpBeerBusinesses()
                    Log.i(TAG, "getYelpBeerBusinesses complete")
                })
                deferredList.add(async {
                    getYelpPizzaBusinesses()
                    Log.i(TAG, "getYelpPizzaBusinesses complete")
                })
                deferredList.add(async {
                    fetchFromRemote()
                    Log.i(TAG, "fetchFromRemote complete")
                })
            }
            // Await all deferreds
            deferredList.awaitAll()
            Log.i(TAG, "getLunchMenu: Completed")
            return@supervisorScope Result.success(cacheMenus)
        } catch (e: Exception) {
            // Handle exceptions that weren't handled in child coroutines
            Log.e(TAG, "Error in getLunchMenu: ${e.message}")
            return@supervisorScope Result.failure(e)
        }
    }

    suspend fun fetchFromRemote() {
        val menus = lunchMenuDataSource.getLunchMenu()

        for (list in menus) {
            for (item in list) {
                val dbItem = LunchMenuItem(name = item)
                lunchMenuDao.insert(dbItem)
            }
        }
    }

    suspend fun getYelpBeerBusinesses(): YelpResponse? {
        sleep(3000)
        return yelpApi.getBeerBusinesses()
    }

    suspend fun getYelpPizzaBusinesses(): YelpResponse? {
//        throw IllegalStateException("An error occurred in getYelpPizzaBusinesses")
        return yelpApi.getPizzaBusinesses()
    }
}