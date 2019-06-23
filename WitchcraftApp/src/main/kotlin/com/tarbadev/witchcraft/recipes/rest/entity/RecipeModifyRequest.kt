package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class RecipeModifyRequest(
    val id: Int = 0,
    val url: String = "",
    val name: String = "",
    val imgUrl: String = "",
    val favorite: Boolean = false,
    val ingredients: List<IngredientModifyRequest> = emptyList(),
    val steps: List<StepModifyRequest> = emptyList()
) {
  fun toRecipe(): Recipe {
    return Recipe(
        id,
        url,
        name,
        imgUrl,
        favorite,
        ingredients.map { it.toIngredient() },
        steps.map { it.toStep() }
    )
  }
}
