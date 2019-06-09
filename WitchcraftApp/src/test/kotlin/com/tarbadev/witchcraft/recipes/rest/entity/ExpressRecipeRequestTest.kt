package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpressRecipeRequestTest {
  @Test
  fun toRecipe() {
    val recipe = Recipe(name = "Lasagna")
    val expressRecipeRequest = ExpressRecipeRequest("Lasagna")

    assertThat(expressRecipeRequest.toRecipe()).isEqualTo(recipe)
  }
}