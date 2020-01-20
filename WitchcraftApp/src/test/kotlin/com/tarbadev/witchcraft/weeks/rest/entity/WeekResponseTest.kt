package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WeekResponseTest {
  @Test
  fun fromWeek() {
    val day = Day(
        meals = listOf(Meal(mealType = MealType.LUNCH, recipe = Recipe()), Meal(mealType = MealType.DINER, recipe = Recipe())),
        name = DayName.FRIDAY,
        id = 2
    )
    val week = Week(
        year = 2018,
        weekNumber = 33,
        days = listOf(day),
        id = 23
    )

    val weekResponse = WeekResponse(
        23,
        2018,
        33,
        listOf(DayResponse.fromDay(day))
    )

    assertThat(WeekResponse.fromWeek(week)).isEqualTo(weekResponse)
  }
}