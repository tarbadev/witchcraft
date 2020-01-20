package com.tarbadev.witchcraft.weeks.rest.entity

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import com.tarbadev.witchcraft.weeks.domain.entity.Meal

data class MealResponse(
    val mealId: Int,
    @JsonUnwrapped val recipe: RecipeResponse
) {
  companion object {
    fun fromMeal(meal: Meal) = MealResponse(
        meal.id,
        RecipeResponse.fromRecipe(meal.recipe)
    )
  }
}
