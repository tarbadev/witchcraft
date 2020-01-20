package com.tarbadev.witchcraft.weeks.domain.usecase

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.springframework.stereotype.Component

@Component
class WeekFromYearAndWeekNumberUseCase(private val weekRepository: WeekRepository) {

  fun execute(year: Int?, weekNumber: Int?): Week {
    var week: Week? = weekRepository.findByYearAndWeekNumber(year!!, weekNumber!!)

    if (week == null) {
      week = Week(
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
          )
      )
    }

    week.days.sortedBy { it.name }

    return week
  }
}
