package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RecipeListResponseTest {
  @Test
  fun `fromRecipeList initiates object from a list of Recipe`() {
    val recipe = Recipe(
            id = 1,
            originUrl = "http://origin",
            imgUrl = "http://imgurl",
            name = "raclette",
            favorite = true,
            ingredients = listOf(Ingredient(
                id = 10,
                name = "Raclette cheese",
                quantity = 2.0,
                unit = "lb"
            )),
            steps = listOf(Step(
                id = 20,
                name = "Make the cheese melt and east, it's good!"
            )),
            isArchived = false
    )

    val expectedRecipeListResponse = RecipeListResponse.fromRecipeList(listOf(recipe))

    assertEquals(RecipeListResponse(listOf(RecipeResponse.fromRecipe(recipe))), expectedRecipeListResponse)
  }
}