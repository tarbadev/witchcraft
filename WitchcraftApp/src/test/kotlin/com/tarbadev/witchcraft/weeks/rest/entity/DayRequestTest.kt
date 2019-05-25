package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DayRequestTest {
  @Test
  fun toWeek() {
    val dayRequest = DayRequest(id = 2, name = DayName.FRIDAY.name, lunch = 25)
    val day = Day(id = 2, name = DayName.FRIDAY, lunch = Recipe(id = 25))

    Assertions.assertThat(dayRequest.toDay()).isEqualTo(day)
  }

  @Test
  fun toWeek_whenLunchOrDinerNull() {
    val dayRequest = DayRequest(id = 2, name = DayName.FRIDAY.name)
    val day = Day(id = 2, name = DayName.FRIDAY)

    Assertions.assertThat(dayRequest.toDay()).isEqualTo(day)
  }
}