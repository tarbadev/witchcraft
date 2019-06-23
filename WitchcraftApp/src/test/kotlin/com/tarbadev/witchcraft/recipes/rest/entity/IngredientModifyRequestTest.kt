package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.converter.cup
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IngredientModifyRequestTest {
  @Test
  fun toIngredient() {
    val ingredientModifyRequest = IngredientModifyRequest(
        id = 1,
        name = "Tomato",
        unit = "cup",
        quantity = 4.0
    )

    val ingredient = Ingredient(
        id = 1,
        name = "Tomato",
        quantity = 4.cup
    )

    assertThat(ingredientModifyRequest.toIngredient()).isEqualTo(ingredient)
  }
}