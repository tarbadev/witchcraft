package com.tarbadev.witchcraft.weeks.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Arrays.asList

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
            days = asList(
                Day(name = DayName.MONDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.TUESDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.WEDNESDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.THURSDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.FRIDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.SATURDAY, lunch = Recipe(), diner = Recipe()),
                Day(name = DayName.SUNDAY, lunch = Recipe(), diner = Recipe())
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
            days = asList(
                Day(
                    name = DayName.MONDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.TUESDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.WEDNESDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.THURSDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.FRIDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.SATURDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                ),
                Day(
                    name = DayName.SUNDAY,
                    lunch = Recipe(),
                    diner = Recipe()
                )
            ))

        whenever(weekRepository.findByYearAndWeekNumber(year, weekNumber)).thenReturn(null)

        assertEquals(week, weekFromYearAndWeekNumberUseCase.execute(year, weekNumber))
    }
}