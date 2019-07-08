package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.converter.pound
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IngredientResponseTest {
  @Test
  fun `constructor initiates object from an Ingredient`() {
    val ingredient = Ingredient(
        id = 10,
        name = "raclette cheese",
        quantity = 2.pound
    )

    val expectedIngredientResponse = IngredientResponse(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.0,
        unit = "lb"
    )

    assertEquals(expectedIngredientResponse, IngredientResponse.fromIngredient(ingredient))
  }

  @Test
  fun `constructor limits digits to 3`() {
    val ingredient = Ingredient(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.5876.pound
    )

    val expectedIngredientResponse = IngredientResponse(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.588,
        unit = "lb"
    )

    assertEquals(expectedIngredientResponse, IngredientResponse.fromIngredient(ingredient))
  }
}