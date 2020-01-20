package com.tarbadev.witchcraft.weeks.domain.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

enum class MealType {
  LUNCH,
  DINER
}

data class Meal(
    val id: Int = 0,
    val mealType: MealType,
    val recipe: Recipe
)
