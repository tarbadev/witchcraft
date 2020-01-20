package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Meal
import com.tarbadev.witchcraft.weeks.domain.entity.MealType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DayResponseTest {
  @Test
  fun fromDay() {
    val day = Day(
        meals = listOf(Meal(recipe = Recipe(id = 3), mealType = MealType.LUNCH, id = 12), Meal(recipe = Recipe(id = 1), mealType = MealType.DINER, id = 78)),
        name = DayName.FRIDAY,
        id = 2
    )

    val dayResponse = DayResponse(
        2,
        "FRIDAY",
        listOf(MealResponse.fromMeal(day.meals[0])),
        listOf(MealResponse.fromMeal(day.meals[1]))
    )

    Assertions.assertThat(DayResponse.fromDay(day)).isEqualTo(dayResponse)
  }
}