package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.cup
import com.tarbadev.witchcraft.converter.tablespoon
import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.converter.unit
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecipeDetailsFromFormUseCaseTest {
  private val ingredientFromStringUseCase: IngredientFromStringUseCase = mock()
  private val convertAndAddSameIngredientsUseCase: ConvertAndAddSameIngredientUseCase = mock()

  private lateinit var subject: GetRecipeDetailsFromFormUseCase

  @BeforeEach
  fun setUp() {
    subject = GetRecipeDetailsFromFormUseCase(ingredientFromStringUseCase, convertAndAddSameIngredientsUseCase)
    reset(
        convertAndAddSameIngredientsUseCase,
        ingredientFromStringUseCase
    )
  }

  @Test
  fun execute() {
    val name = "Some recipe name"
    val originUrl = "http://some/originUrl/of/recipe"
    val imgUrl = "http://some/originUrl/of/recipe.png"
    val ingredients = arrayOf("10 tbsp sugar", "1/2 cup olive oil", "1 lemon").joinToString("\n")
    val steps = arrayOf("Add ingredients and stir", "Serve on each plate").joinToString("\n")
    val portions = 4

    val ingredient1 = Ingredient(
        name = "sugar",
        quantity = 10.tablespoon
    )
    val ingredient2 = Ingredient(
        name = "olive oil",
        quantity = 0.5.cup
    )
    val ingredient3 = Ingredient(
        name = "lemon",
        quantity = 1.unit
    )
    val recipe = Recipe(
        name = name,
        originUrl = originUrl,
        imgUrl = imgUrl,
        ingredients = listOf(
            ingredient1,
            ingredient2,
            ingredient3
        ),
        steps = steps.split("\n")
            .dropLastWhile { it.isEmpty() }
            .map { step -> Step(name = step) },
        portions = portions
    )


    whenever(ingredientFromStringUseCase.execute("10 tbsp sugar")).thenReturn(ingredient1)
    whenever(ingredientFromStringUseCase.execute("1/2 cup olive oil")).thenReturn(ingredient2)
    whenever(ingredientFromStringUseCase.execute("1 lemon")).thenReturn(ingredient3)
    whenever(convertAndAddSameIngredientsUseCase.execute(recipe.ingredients))
        .thenReturn(recipe.ingredients)

    assertEquals(recipe, subject.execute(name, originUrl, ingredients, steps, imgUrl, portions))
  }

  @Test
  fun execute_addsSameIngredients() {
    val name = "Some recipe name"
    val url = "http://some/originUrl/of/recipe"
    val imgUrl = "http://some/originUrl/of/recipe.png"
    val ingredients = arrayOf("10 tbsp sugar", "1/2 cup olive oil", "3 tsp olive oil", "1 lemon").joinToString("\n")
    val steps = arrayOf("Add ingredients and stir", "Serve on each plate").joinToString("\n")
    val portions = 4

    val ingredient1 = Ingredient(
        name = "sugar",
        quantity = 10.tablespoon
    )
    val ingredient2 = Ingredient(
        name = "olive oil",
        quantity = 0.5.cup
    )
    val ingredient3 = Ingredient(
        name = "olive oil",
        quantity = 3.teaspoon
    )
    val ingredient4 = Ingredient(
        name = "lemon",
        quantity = 1.unit
    )
    val allIngredients = listOf(
        ingredient1,
        ingredient2,
        ingredient3,
        ingredient4
    )

    val expectedIngredients = listOf(
        Ingredient(
            name = "sugar",
            quantity = 10.tablespoon
        ),
        Ingredient(
            name = "olive oil",
            quantity = 0.5625.cup
        ),
        Ingredient(
            name = "lemon",
            quantity = 1.unit
        )
    )
    val recipe = Recipe(
        name = name,
        originUrl = url,
        imgUrl = imgUrl,
        ingredients = expectedIngredients,
        steps = steps.split("\n")
            .dropLastWhile { it.isEmpty() }
            .map { step -> Step(name = step) },
        portions = portions
    )

    whenever(ingredientFromStringUseCase.execute("10 tbsp sugar")).thenReturn(ingredient1)
    whenever(ingredientFromStringUseCase.execute("1/2 cup olive oil")).thenReturn(ingredient2)
    whenever(ingredientFromStringUseCase.execute("3 tsp olive oil")).thenReturn(ingredient3)
    whenever(ingredientFromStringUseCase.execute("1 lemon")).thenReturn(ingredient4)
    whenever(convertAndAddSameIngredientsUseCase.execute(allIngredients)).thenReturn(expectedIngredients)

    assertEquals(recipe, subject.execute(name, url, ingredients, steps, imgUrl, portions))
  }
}