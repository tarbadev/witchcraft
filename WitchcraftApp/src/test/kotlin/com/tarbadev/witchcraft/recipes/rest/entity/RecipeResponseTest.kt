package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.converter.pound
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RecipeResponseTest {
  @Test
  fun `constructor initiates object from a Recipe`() {
    val ingredient = Ingredient(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.pound
    )
    val step = Step(
        id = 20,
        name = "Make the cheese melt and east, it's good!"
    )
    val recipe = Recipe(
            id = 1,
            originUrl = "http://origin",
            imgUrl = "http://imgurl",
            name = "raclette",
            favorite = true,
            ingredients = listOf(ingredient),
            steps = listOf(step),
            isArchived = false
    )

    val expectedRecipeResponse = RecipeResponse(
        id = 1,
        originUrl = "http://origin",
        imgUrl = "http://imgurl",
        name = "raclette",
        favorite = true,
        ingredients = listOf(IngredientResponse.fromIngredient(ingredient)),
        steps = listOf(StepResponse.fromStep(step)),
        isArchived = false
    )

    assertEquals(expectedRecipeResponse, RecipeResponse.fromRecipe(recipe))
  }
}