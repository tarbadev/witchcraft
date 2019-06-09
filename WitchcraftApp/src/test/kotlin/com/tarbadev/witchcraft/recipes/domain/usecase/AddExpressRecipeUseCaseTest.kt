package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddExpressRecipeUseCaseTest {
  private val recipeRepository: RecipeRepository = mock()
  private val addExpressRecipeUseCase = AddExpressRecipeUseCase(recipeRepository)

  @Test
  fun execute() {
    val recipe = Recipe(name = "Lasagna")
    val updatedRecipe = recipe.copy(id = 3)

    whenever(recipeRepository.save(recipe)).thenReturn(updatedRecipe)

    assertThat(addExpressRecipeUseCase.execute(recipe)).isEqualTo(updatedRecipe)
  }
}