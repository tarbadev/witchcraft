package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Week

data class WeekRequest(
    val id: Int = 0,
    val year: Int = 0,
    val weekNumber: Int = 0,
    val days: List<DayRequest> = emptyList()
) {
  fun toWeek(): Week {
    return Week(id, year, weekNumber, days.map { it.toDay() })
  }
}
