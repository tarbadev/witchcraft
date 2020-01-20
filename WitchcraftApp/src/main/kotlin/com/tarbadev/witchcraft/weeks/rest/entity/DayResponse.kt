package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.MealType

data class DayResponse(
    val id: Int,
    val name: String,
    val lunch: List<MealResponse>,
    val diner: List<MealResponse>
) {
  companion object {
    fun fromDay(day: Day) = DayResponse(
        day.id,
        day.name.name,
        day.meals.filter { it.mealType == MealType.LUNCH }
            .map { MealResponse.fromMeal(it) },
        day.meals.filter { it.mealType == MealType.DINER }
            .map { MealResponse.fromMeal(it) }
    )
  }
}
