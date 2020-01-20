package com.tarbadev.witchcraft.weeks.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.*
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WeekFromYearAndWeekNumberUseCaseTest {
    private val weekRepository: WeekRepository = mock()
    private lateinit var weekFromYearAndWeekNumberUseCase: WeekFromYearAndWeekNumberUseCase

    @BeforeEach
    fun setUp() {
        weekFromYearAndWeekNumberUseCase = WeekFromYearAndWeekNumberUseCase(weekRepository)
    }

    @Test
    fun execute() {
        val week = Week(
            weekNumber = 12,
            year = 2018,
            days = listOf(
                Day(name = DayName.MONDAY, meals = listOf(Meal(mealType = MealType.LUNCH, recipe= Recipe()))),
                Day(name = DayName.TUESDAY, meals = listOf(Meal(mealType = MealType.DINER, recipe= Recipe()))),
                Day(name = DayName.WEDNESDAY, meals = listOf(Meal(mealType = MealType.LUNCH, recipe= Recipe()))),
                Day(name = DayName.THURSDAY, meals = listOf(Meal(mealType = MealType.LUNCH, recipe= Recipe()))),
                Day(name = DayName.FRIDAY, meals = listOf(Meal(mealType = MealType.DINER, recipe= Recipe()))),
                Day(name = DayName.SATURDAY, meals = listOf(Meal(mealType = MealType.LUNCH, recipe= Recipe()))),
                Day(name = DayName.SUNDAY, meals = listOf(Meal(mealType = MealType.DINER, recipe= Recipe())))
            ))

        whenever(weekRepository.findByYearAndWeekNumber(week.year, week.weekNumber)).thenReturn(week)

        assertEquals(week, weekFromYearAndWeekNumberUseCase.execute(week.year, week.weekNumber))
    }

    @Test
    fun execute_databaseNullReturnsNewWeek() {
        val year = 2018
        val weekNumber = 12
        val week = Week(
            year = year,
            weekNumber = weekNumber,
            days = listOf(
                Day(name = DayName.MONDAY),
                Day(name = DayName.TUESDAY),
                Day(name = DayName.WEDNESDAY),
                Day(name = DayName.THURSDAY),
                Day(name = DayName.FRIDAY),
                Day(name = DayName.SATURDAY),
                Day(name = DayName.SUNDAY)
            ))

        whenever(weekRepository.findByYearAndWeekNumber(year, weekNumber)).thenReturn(null)

        assertEquals(week, weekFromYearAndWeekNumberUseCase.execute(year, weekNumber))
    }
}