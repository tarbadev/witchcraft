package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.IngredientConverter
import com.tarbadev.witchcraft.converter.cup
import com.tarbadev.witchcraft.converter.tablespoon
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class ConvertAndAddSameIngredientUseCaseTest {
  private val ingredientConverter: IngredientConverter = mock()
  private lateinit var subject: ConvertAndAddSameIngredientUseCase

  @BeforeEach
  fun setUp() {
    subject = ConvertAndAddSameIngredientUseCase(ingredientConverter)
    reset(ingredientConverter)
  }

  @Test
  fun execute() {
    val allIngredients = listOf(
        Ingredient(name = "olive oil", quantity = 0.5.cup),
        Ingredient(name = "olive oil", quantity = 3.tablespoon)
    )

    val expectedIngredient = Ingredient(
        name = "olive oil",
        quantity = 0.5625.cup
    )

    whenever(ingredientConverter.addToHighestUnit(allIngredients[0], allIngredients[1]))
        .thenReturn(expectedIngredient)

    assertEquals(listOf(expectedIngredient), subject.execute(allIngredients))
  }
}