package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdatePortionsUseCaseTest {
  private val recipeRepository: RecipeRepository = mock()
  private lateinit var subject: UpdatePortionsUseCase

  @BeforeEach
  fun setUp() {
    subject = UpdatePortionsUseCase(recipeRepository)
  }

  @Test
  fun execute() {
    val id = 123
    val portions = 8
    val recipe = Recipe()
    val updatedRecipe = recipe.copy(portions = portions)

    whenever(recipeRepository.findById(id)).thenReturn(recipe)
    whenever(recipeRepository.save(any())).thenReturn(updatedRecipe)

    assertEquals(subject.execute(id, portions), updatedRecipe)

    verify(recipeRepository).save(updatedRecipe)
  }
}