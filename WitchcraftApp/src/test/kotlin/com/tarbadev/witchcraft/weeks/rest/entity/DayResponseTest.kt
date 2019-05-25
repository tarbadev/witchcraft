package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DayResponseTest {
  @Test
  fun fromDay() {
    val day = Day(lunch = Recipe(id = 3), diner = Recipe(id = 1), name = DayName.FRIDAY, id = 2)

    val dayResponse = DayResponse(
        2,
        "FRIDAY",
        RecipeResponse.fromRecipe(day.lunch!!),
        RecipeResponse.fromRecipe(day.diner!!)
    )

    Assertions.assertThat(DayResponse.fromDay(day)).isEqualTo(dayResponse)
  }
}