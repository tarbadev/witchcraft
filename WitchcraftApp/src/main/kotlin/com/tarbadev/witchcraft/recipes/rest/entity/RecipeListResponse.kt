package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class RecipeListResponse(
    val recipes: List<RecipeResponse>
) {
  companion object {
    fun fromRecipeList(recipes: List<Recipe>): RecipeListResponse =
        RecipeListResponse(recipes = recipes.map { RecipeResponse.fromRecipe(it) })
  }
}