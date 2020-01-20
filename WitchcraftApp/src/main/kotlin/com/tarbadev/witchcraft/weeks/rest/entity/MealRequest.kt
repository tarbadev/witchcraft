package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Meal
import com.tarbadev.witchcraft.weeks.domain.entity.MealType

data class MealRequest(
    val mealId: Int,
    val recipeId: Int
) {
  fun toMeal(mealType: MealType): Meal {
    return Meal(
        id = mealId,
        recipe = Recipe(id = recipeId),
        mealType = mealType
    )
  }
}
