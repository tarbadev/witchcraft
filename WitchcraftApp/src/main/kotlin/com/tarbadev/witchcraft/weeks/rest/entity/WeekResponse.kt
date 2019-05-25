package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Week

data class WeekResponse(
    val id: Int = 0,
    val year: Int = 0,
    val weekNumber: Int = 0,
    val days: List<DayResponse> = emptyList()
) {
  companion object {
    fun fromWeek(week: Week): WeekResponse {
      return WeekResponse(
          week.id,
          week.year,
          week.weekNumber,
          week.days.map { DayResponse.fromDay(it) }
      )
    }
  }
}
