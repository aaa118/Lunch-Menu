package com.lunchmenu.home.domain.usecase

import com.lunchmenu.home.data.datasource.local.entity.LunchMenuItem
import com.lunchmenu.home.data.repository.LunchMenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ConvertLunchMenuForCalendarUseCase @Inject constructor(private val lunchMenuRepository: LunchMenuRepository) {

    suspend operator fun invoke(): Flow<MutableList<Pair<String, String>>> =
        withContext(Dispatchers.IO) {
            lunchMenuRepository.getLunchMenu().fold(
                onSuccess = { flowOfLists ->
                    return@withContext flowOfLists.map { mapListsOfMenuToDaysOfTheWeek(it) }
                },
                // This can throw an exception also to be handled by the viewmodel to go in an error state
                onFailure = {
                    return@withContext flowOf(mutableListOf(Pair("No Data", "No Data")))
                }
            )
        }

    private fun mapListsOfMenuToDaysOfTheWeek(listsOfMenu: List<LunchMenuItem>): MutableList<Pair<String, String>> {
        val daysOfWeek = listOf(
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY
        )
        // Flatten the list of meals to a single list
        val allMeals = listsOfMenu.map { it.name }
        // Get the current date and time
        val calendar = Calendar.getInstance()
        // Find the first upcoming Monday, this ensure calendar always start with upcoming Monday.
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1) // Move to the next day until it's Monday
        }
        val dateFormatter = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        val mealCalendarMap = mutableListOf<Pair<String, String>>()
        var mealIndex = 0
        // Loop over all the weeks and map them to 5 days each, will map to 10 days based on
        // the static data, but should be scalable to more data also
        while (mealIndex < allMeals.size) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            // If it's a weekday add it to the meal map
            if (dayOfWeek in daysOfWeek) {
                val formattedDate = dateFormatter.format(calendar.time)
                mealCalendarMap.add(Pair(formattedDate, allMeals[mealIndex]))
                mealIndex++
            }
            // Move to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return mealCalendarMap
    }

}