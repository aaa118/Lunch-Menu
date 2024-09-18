package com.lunchmenu.home.data.datasource.remote

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lunchmenu.home.data.repository.LunchMenuRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class HomeRemoteWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val lunchMenuRepository: LunchMenuRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Do the work here
        lunchMenuRepository.fetchFromRemote()
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}