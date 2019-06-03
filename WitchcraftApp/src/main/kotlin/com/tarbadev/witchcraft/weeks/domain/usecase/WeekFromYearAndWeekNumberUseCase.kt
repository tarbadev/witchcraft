package com.tarbadev.witchcraft.weeks.domain.usecase

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class WeekFromYearAndWeekNumberUseCase(private val weekRepository: WeekRepository) {

  fun execute(year: Int?, weekNumber: Int?): Week {
    var week: Week? = weekRepository.findByYearAndWeekNumber(year!!, weekNumber!!)

    if (week == null) {
      week = Week(
          year = year,
          weekNumber = weekNumber,
          days = Arrays.asList(
              Day(name = DayName.MONDAY, lunch = null, diner = null),
              Day(name = DayName.TUESDAY, lunch = null, diner = null),
              Day(name = DayName.WEDNESDAY, lunch = null, diner = null),
              Day(name = DayName.THURSDAY, lunch = null, diner = null),
              Day(name = DayName.FRIDAY, lunch = null, diner = null),
              Day(name = DayName.SATURDAY, lunch = null, diner = null),
              Day(name = DayName.SUNDAY, lunch = null, diner = null)
          )
      )
    }

    week.days.sortedBy { it.name }

    return week
  }
}
