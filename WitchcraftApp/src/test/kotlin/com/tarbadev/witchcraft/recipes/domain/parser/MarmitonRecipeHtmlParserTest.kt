package com.tarbadev.witchcraft.recipes.domain.parser

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.converter.centiliter
import com.tarbadev.witchcraft.converter.gram
import com.tarbadev.witchcraft.converter.tablespoon
import com.tarbadev.witchcraft.converter.unit
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MarmitonRecipeHtmlParserTest {
  private val recipe = TestResources.Recipes.marmiton
  private lateinit var subject: MarmitonRecipeHtmlParser

  private val ingredientFromStringUseCase: IngredientFromStringUseCase = mock()
  private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase = mock()

  private val ingredient1 = Ingredient(
      name = "Farine",
      quantity = 300.gram
  )
  private val ingredient2 = Ingredient(
      name = "Oeufs entiers",
      quantity = 3.unit
  )
  private val ingredient3 = Ingredient(
      name = "Sucre",
      quantity = 3.tablespoon
  )
  private val ingredient4 = Ingredient(
      name = "Huile",
      quantity = 2.tablespoon
  )
  private val ingredient5 = Ingredient(
      name = "Beurre fondu",
      quantity = 50.gram
  )
  private val ingredient6 = Ingredient(
      name = "Lait",
      quantity = 60.centiliter
  )
  private val ingredient7 = Ingredient(
      name = "Rhum",
      quantity = 5.centiliter
  )

  @BeforeEach
  fun setUp() {
    subject = MarmitonRecipeHtmlParser(ingredientFromStringUseCase, convertAndAddSameIngredientUseCase)

    reset(
        convertAndAddSameIngredientUseCase,
        ingredientFromStringUseCase
    )

    whenever(ingredientFromStringUseCase.execute(any())).thenReturn(Ingredient(quantity = 1.unit))
  }

  @Test
  fun parse() {
    mockParsingOfIngredients()

    val returnedRecipe = subject.parse(recipe.originUrl)
    assertEquals(recipe, returnedRecipe)
  }

  @Test
  fun parse_getsRecipeName() {
    assertEquals(recipe.name, subject.parse(recipe.originUrl).name)
  }

  @Test
  fun parse_getsRecipeIngredients() {
    mockParsingOfIngredients()

    val ingredients = subject.parse(recipe.originUrl).ingredients
    assertEquals(recipe.ingredients, ingredients)
  }

  @Test
  fun parse_getsRecipeImageUrl() {
    val imgUrl = subject.parse(recipe.originUrl).imgUrl
    assertEquals(recipe.imgUrl, imgUrl)
  }

  @Test
  fun parse_getsRecipesSteps() {
    val steps = subject.parse(recipe.originUrl).steps

    assertEquals(recipe.steps, steps)
  }

  private fun mockParsingOfIngredients() {
    whenever(ingredientFromStringUseCase.execute(any()))
        .thenReturn(ingredient1)
        .thenReturn(ingredient2)
        .thenReturn(ingredient3)
        .thenReturn(ingredient4)
        .thenReturn(ingredient5)
        .thenReturn(ingredient6)
        .thenReturn(ingredient7)
    whenever(convertAndAddSameIngredientUseCase.execute(
        listOf(
            ingredient1,
            ingredient2,
            ingredient3,
            ingredient4,
            ingredient5,
            ingredient6,
            ingredient7
        )
    ))
        .thenReturn(recipe.ingredients)
  }
}