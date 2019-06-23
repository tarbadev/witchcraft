package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipeModifyRequestTest {
  @Test
  fun toRecipe() {
    val ingredientModifyRequest = IngredientModifyRequest(
        id = 1,
        name = "some toIngredient",
        quantity = 2.0,
        unit = "cup"
    )
    val stepModifyRequest = StepModifyRequest(
        id = 65,
        name = "Some Step"
    )

    val recipeModifyRequest = RecipeModifyRequest(
        id = 34,
        url = "Some url",
        name = "Dummy recipe",
        imgUrl = "Some Url",
        favorite = true,
        ingredients = listOf(ingredientModifyRequest),
        steps = listOf(stepModifyRequest)
    )
    val recipe = Recipe(
        id = 34,
        originUrl = "Some url",
        name = "Dummy recipe",
        imgUrl = "Some Url",
        ingredients = listOf(ingredientModifyRequest.toIngredient()),
        steps = listOf(stepModifyRequest.toStep()),
        favorite = true,
        isArchived = false,
        portions = null
    )

    assertThat(recipeModifyRequest.toRecipe()).isEqualTo(recipe)
  }
}