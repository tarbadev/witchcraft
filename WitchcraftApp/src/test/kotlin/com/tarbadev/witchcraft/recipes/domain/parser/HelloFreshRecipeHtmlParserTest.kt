package com.tarbadev.witchcraft.recipes.domain.parser

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Arrays.asList

class HelloFreshRecipeHtmlParserTest {
  private val recipe = TestResources.Recipes.helloFresh
  private lateinit var subject: HelloFreshRecipeHtmlParser

  private val ingredientFromStringUseCase: IngredientFromStringUseCase = mock()
  private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase = mock()

  private val ingredient1 = Ingredient(
      name = "Red Onion",
      quantity = 1.0
  )
  private val ingredient2 = Ingredient(
      name = "Blackening Spice",
      quantity = 1.0,
      unit = "tbsp"
  )
  private val ingredient3 = Ingredient(
      name = "Roma Tomato",
      quantity = 1.0
  )
  private val ingredient4 = Ingredient(
      name = "Flour Tortilla",
      quantity = 2.0
  )
  private val ingredient5 = Ingredient(
      name = "Mexican Cheese Blend",
      quantity = 0.5,
      unit = "cup"
  )
  private val ingredient6 = Ingredient(
      name = "Chicken Breast Strips",
      quantity = 10.0,
      unit = "oz"
  )
  private val ingredient7 = Ingredient(
      name = "Lime",
      quantity = 1.0
  )
  private val ingredient8 = Ingredient(
      name = "Sour Cream",
      quantity = 2.0,
      unit = "tbsp"
  )
  private val ingredient9 = Ingredient(
      name = "Mozzarella Cheese",
      quantity = 0.5,
      unit = "cup"
  )
  private val ingredient10 = Ingredient(
      name = "Hot Sauce",
      quantity = 1.0,
      unit = "tsp"
  )
  private val ingredient11 = Ingredient(
      name = "Vegetable Oil",
      quantity = 4.0,
      unit = "tsp"
  )
  private val ingredient12 = Ingredient(
      name = "Butter",
      quantity = 2.0,
      unit = "tbsp"
  )
  private val ingredient13 = Ingredient(
      name = "Salt",
      quantity = 1.0
  )
  private val ingredient14 = Ingredient(
      name = "Pepper",
      quantity = 1.0
  )

  @BeforeEach
  fun setUp() {
    subject = HelloFreshRecipeHtmlParser(ingredientFromStringUseCase, convertAndAddSameIngredientUseCase)

    reset(
        convertAndAddSameIngredientUseCase,
        ingredientFromStringUseCase
    )

    whenever(ingredientFromStringUseCase.execute(any())).thenReturn(Ingredient())
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

    assertEquals(6, steps.size)
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
        .thenReturn(ingredient8)
        .thenReturn(ingredient9)
        .thenReturn(ingredient10)
        .thenReturn(ingredient11)
        .thenReturn(ingredient12)
        .thenReturn(ingredient13)
        .thenReturn(ingredient14)
    whenever(convertAndAddSameIngredientUseCase.execute(
        asList(
            ingredient1,
            ingredient2,
            ingredient3,
            ingredient4,
            ingredient5,
            ingredient6,
            ingredient7,
            ingredient8,
            ingredient9,
            ingredient10,
            ingredient11,
            ingredient12,
            ingredient13,
            ingredient14
        )
    ))
        .thenReturn(recipe.ingredients)
  }
}