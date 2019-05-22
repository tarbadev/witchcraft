package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class RecipeResponse(
    val id: Int,
    val originUrl: String,
    val name: String,
    val imgUrl: String,
    val favorite: Boolean,
    val ingredients: List<IngredientResponse>,
    val steps: List<StepResponse>,
    val isArchived: Boolean
) {
  companion object {
    fun fromRecipe(recipe: Recipe): RecipeResponse =
        RecipeResponse(
            id = recipe.id,
            originUrl = recipe.originUrl,
            name = recipe.name,
            imgUrl = recipe.imgUrl,
            favorite = recipe.favorite,
            ingredients = recipe.ingredients.map { IngredientResponse.fromIngredient(it) },
            steps = recipe.steps.map { StepResponse.fromStep(it) },
            isArchived = recipe.isArchived
        )
  }
}