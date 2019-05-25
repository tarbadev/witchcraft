package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WeekRequestTest {
  @Test
  fun toWeek() {
    val weekRequest = WeekRequest(
        id = 1,
        weekNumber = 23,
        year = 2019,
        days = listOf(DayRequest(id = 2, name = DayName.FRIDAY.name, lunch = 25))
    )

    val week = Week(
        id = 1,
        weekNumber = 23,
        year = 2019,
        days = listOf(Day(id = 2, name = DayName.FRIDAY, lunch = Recipe(id = 25)))
    )

    assertThat(weekRequest.toWeek()).isEqualTo(week)
  }
}