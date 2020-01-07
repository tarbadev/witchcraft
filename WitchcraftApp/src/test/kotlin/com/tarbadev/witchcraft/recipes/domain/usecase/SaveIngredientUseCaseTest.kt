package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.ounce
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.repository.IngredientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveIngredientUseCaseTest {
  private val ingredientRepository: IngredientRepository = mock()
  private lateinit var saveIngredientUseCase: SaveIngredientUseCase

  @BeforeEach
  fun setUp() {
    saveIngredientUseCase = SaveIngredientUseCase(ingredientRepository)
  }

  @Test
  fun execute() {
    val ingredient = Ingredient(
        id = 23,
        name = "Some Ingredient",
        quantity = 2.ounce
    )

    whenever(ingredientRepository.save(any())).thenReturn(ingredient)

    Assertions.assertEquals(ingredient, saveIngredientUseCase.execute(ingredient))
  }
}