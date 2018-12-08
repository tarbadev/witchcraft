package com.tarbadev.witchcraft.weeks.domain.usecase

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.springframework.stereotype.Component

import java.util.Arrays
import java.util.Comparator

@Component
class WeekFromYearAndWeekNumberUseCase(private val weekRepository: WeekRepository) {

    fun execute(year: Int?, weekNumber: Int?): Week {
        var week: Week? = weekRepository.findByYearAndWeekNumber(year!!, weekNumber!!)

        if (week == null) {
            week = Week(
                    year = year,
                    weekNumber = weekNumber,
                    days = Arrays.asList(
                            Day(name = DayName.MONDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.TUESDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.WEDNESDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.THURSDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.FRIDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.SATURDAY, lunch = Recipe(), diner = Recipe()),
                            Day(name = DayName.SUNDAY, lunch = Recipe(), diner = Recipe())
                    )
            )
        }

        week.days.sortedBy { it.name }

        return week
    }
}
