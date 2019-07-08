package com.tarbadev.witchcraft.recipes.domain.parser

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.converter.*
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Arrays.asList

class CookinCanuckRecipeHtmlParserTest {
  private val recipe = TestResources.Recipes.cookinCanuck
  private lateinit var subject: CookinCanuckRecipeHtmlParser

  private val ingredientFromStringUseCase: IngredientFromStringUseCase = mock()
  private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase = mock()

  private val ingredient1 = Ingredient(
      name = "Little Potato Co. Creamer potatoes (I used Dynamic Duo)",
      quantity = 1.5.pound
  )
  private val ingredient2 = Ingredient(
      name = "soft goat cheese (chevre), room temperature",
      quantity = 2.ounce
  )
  private val ingredient3 = Ingredient(
      name = "diced roasted red pepper",
      quantity = 3.tablespoon
  )
  private val ingredient4 = Ingredient(
      name = "pitted Kalamata olives, diced",
      quantity = 4.unit
  )
  private val ingredient5 = Ingredient(
      name = "minced flat-leaf parsley",
      quantity = 1.tablespoon
  )
  private val ingredient6 = Ingredient(
      name = "soft goat cheese (chevre), room temperature",
      quantity = 2.ounce
  )
  private val ingredient7 = Ingredient(
      name = "pistachios halves, divided",
      quantity = 3.tablespoon
  )
  private val ingredient8 = Ingredient(
      name = "honey",
      quantity = 1.teaspoon
  )
  private val ingredient9 = Ingredient(
      name = "ground cinnamon",
      quantity = 0.25.teaspoon
  )

  @BeforeEach
  fun setUp() {
    subject = CookinCanuckRecipeHtmlParser(ingredientFromStringUseCase, convertAndAddSameIngredientUseCase)

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
    whenever(convertAndAddSameIngredientUseCase.execute(recipe.ingredients))
        .thenReturn(recipe.ingredients)

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

  @Test
  fun parse_ImportStepsFromParagraphTag() {
    val steps1 = subject.parse("https://www.cookincanuck.com/one-pot-whole-wheat-pasta-recipe-chicken-spinach/").steps

    val steps = asList(
        Step(name = "In a large nonstick saucepan (or nonstick skillet with high sides) set over medium-high heat, heat 2 teaspoons of olive oil. Add the chicken and cook, stirring occasionally, until just cooked through, 4 to 5 minutes. Transfer to a bowl."),
        Step(name = "Reduce the heat to medium and add 1 teaspoon of olive oil to the saucepan. Add the orange pepper and cook for 1 minute. Transfer to the bowl with the chicken. Set aside."),
        Step(name = "Add the remaining 1 teaspoon of olive oil to the saucepan. Add the onion and sauté until softened, about 4 minutes. Add the garlic and oregano, and cook for 30 seconds."),
        Step(name = "Pour in the diced tomatoes, chicken broth and balsamic vinegar. Bring to a boil, then stir in the pasta, stirring to coat and submerge the pasta."),
        Step(name = "Cover and simmer until the pasta is al dente (see note)."),
        Step(name = "Stir in the chicken, bell pepper, spinach and parsley, and stir to wilt the spinach. Remove from the heat and sprinkle with the feta cheese. Taste and add salt, if desired. Serve.")
    )

    assertEquals(steps, steps1)
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
            ingredient9
        )
    ))
        .thenReturn(recipe.ingredients)
  }
}
