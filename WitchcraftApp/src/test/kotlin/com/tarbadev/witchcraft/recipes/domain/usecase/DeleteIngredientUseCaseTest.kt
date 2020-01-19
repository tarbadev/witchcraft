package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tarbadev.witchcraft.recipes.domain.repository.IngredientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteIngredientUseCaseTest {
  private val ingredientRepository: IngredientRepository = mock()
  private lateinit var deleteIngredientUseCase: DeleteIngredientUseCase

  @BeforeEach
  fun setUp() {
    deleteIngredientUseCase = DeleteIngredientUseCase(ingredientRepository)
  }

  @Test
  fun execute() {
    deleteIngredientUseCase.execute(23)

    verify(ingredientRepository).delete(23)
  }
}