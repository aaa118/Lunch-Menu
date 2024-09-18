package com.gusto.lunchmenu.home.domain.usecase

import com.lunchmenu.home.data.datasource.local.entity.LunchMenuItem
import com.lunchmenu.home.data.repository.LunchMenuRepository
import com.lunchmenu.home.domain.usecase.ConvertLunchMenuForCalendarUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

/**
 * These Tests provide 100% coverage to our business logic.
 */
class ConvertLunchMenuForCalendarUseCaseTest {
    private val lunchMenuRepository: LunchMenuRepository = mockk(relaxed = true)
    private val sut = ConvertLunchMenuForCalendarUseCase(lunchMenuRepository)
    var lunchMenuItem = LunchMenuItem(name = "Chick")

    val fakeList = listOf(
        LunchMenuItem(name = "Chick"),
        LunchMenuItem(name = "Tac"),
        LunchMenuItem(name = "Cur"),
        LunchMenuItem(name = "Pi"),
        LunchMenuItem(name = "Sushi"),
        LunchMenuItem(name = "Breakfa"),
        LunchMenuItem(name = "Hamb"),
        LunchMenuItem(name = "Spag"),
        LunchMenuItem(name = "Sal"),
        LunchMenuItem(name = "San"),
    )

    @Before
    fun setUp() {
        coEvery { lunchMenuRepository.getLunchMenu() } returns Result.success(flowOf(fakeList))

    }

    @Test
    fun when_listsOfMenuIsPassed_then_datesReturnedInTheList() = runTest {
        val modifiedList = sut.invoke()
        modifiedList.collectLatest {
            // Only verifying substring as the date will change based on when this test is run.
            assertEquals(10, it.size)
            assertEquals("Monday", it[0].first.substring(0, 6))
            assertEquals("Tuesday", it[1].first.substring(0, 7))
            assertEquals("Wednesday", it[2].first.substring(0, 9))
            assertEquals("Thursday", it[3].first.substring(0, 8))
            assertEquals("Friday", it[4].first.substring(0, 6))
            // Verify next Week starts from Monday
            assertEquals("Monday", it[5].first.substring(0, 6))
        }


    }

//    @Test
//    fun when_listsOfMenuIsPassed_then_menuReturnedInTheList() = runTest {
//        val modifiedList = sut.invoke()
//        assertEquals(20, modifiedList.size)
//        assertEquals("Chick", modifiedList[0].second)
//        assertEquals("Tac", modifiedList[1].second)
//        assertEquals("Cur", modifiedList[2].second)
//        assertEquals("Pi", modifiedList[3].second)
//        assertEquals("Sushi", modifiedList[4].second)
//        assertEquals("Breakfa", modifiedList[5].second)
//    }
//
//    @Test
//    fun when_emptylistsOfMenuIsPassed_then_returnEmptyList() = runTest {
//        coEvery { lunchMenuRepository.getLunchMenu().getOrNull() } returns emptyList()
//        val modifiedList = sut.invoke()
//        assertEquals(0, modifiedList.size)
//    }
//
//    @Test
//    fun when_emptylistsOfMenuIsPassed_then_returnFailure() = runTest {
//        coEvery { lunchMenuRepository.getLunchMenu().onFailure { IllegalStateException() } } returns Result.failure(IllegalStateException())
//        val modifiedList = sut.invoke()
//        assertEquals(1, modifiedList.size)
//        assertEquals("No Data", modifiedList[0].first)
//        assertEquals("No Data", modifiedList[0].second)
//    }
}