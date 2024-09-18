package com.lunchmenu.home.data.datasource.local

import kotlinx.coroutines.delay
import javax.inject.Inject

class LunchMenuDataSource @Inject constructor() {

    suspend fun getLunchMenu(): List<List<String>> {
        delay(3_000)
        return listOf(
            listOf("Chicken and waffles", "Tacos", "Curry", "Pizza", "Sushi"),
            listOf("Breakfast for lunch", "Hamburgers", "Spaghetti", "Salmon", "Sandwiches"),
            listOf("Chicken and waffles", "Tacos", "Curry", "Pizza", "Sushi"),
            listOf("Breakfast for lunch", "Hamburgers", "Spaghetti", "Salmon", "Sandwiches"),
            listOf("Chicken and waffles", "Tacos", "Curry", "Pizza", "Sushi"),
            listOf("Breakfast for lunch", "Hamburgers", "Spaghetti", "Salmon", "Sandwiches"),
            listOf("Chicken and waffles", "Tacos", "Curry", "Pizza", "Sushi"),
            listOf("Breakfast for lunch", "Hamburgers", "Spaghetti", "Salmon", "Sandwiches"),
        )
    }
}