package com.tarbadev.witchcraft.recipes.domain

import com.nhaarman.mockitokotlin2.*
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.Arrays.asList

class CookinCanuckRecipeHtmlParserTest {
    private lateinit var subject: CookinCanuckRecipeHtmlParser

    private val testResources: TestResources = TestResources()
    private val ingredientFromStringUseCase: IngredientFromStringUseCase = mock()
    private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase = mock()

    @BeforeEach
    fun setUp() {
        subject = CookinCanuckRecipeHtmlParser(ingredientFromStringUseCase, convertAndAddSameIngredientUseCase)

        reset(
            convertAndAddSameIngredientUseCase,
            ingredientFromStringUseCase
        )

        whenever(ingredientFromStringUseCase.execute(any())).thenReturn(Ingredient())
    }

    @Test
    fun parse() {
        val recipe = testResources.recipe
        val ingredient1 = Ingredient(
            name = "Little Potato Co. Creamer potatoes (I used Dynamic Duo)",
            quantity = 1.5,
            unit = "lb"
        )
        val ingredient2 = Ingredient(
            name = "soft goat cheese (chevre), room temperature",
            quantity = 2.0,
            unit = "oz"
        )
        val ingredient3 = Ingredient(
            name = "diced roasted red pepper",
            quantity = 3.0,
            unit = "tbsp"
        )
        val ingredient4 = Ingredient(
            name = "pitted Kalamata olives, diced",
            quantity = 4.0,
            unit = ""
        )
        val ingredient5 = Ingredient(
            name = "minced flat-leaf parsley",
            quantity = 1.0,
            unit = "tbsp"
        )
        val ingredient6 = Ingredient(
            name = "soft goat cheese (chevre), room temperature",
            quantity = 2.0,
            unit = "oz"
        )
        val ingredient7 = Ingredient(
            name = "pistachios halves, divided",
            quantity = 3.0,
            unit = "tbsp"
        )
        val ingredient8 = Ingredient(
            name = "honey",
            quantity = 1.0,
            unit = "tsp"
        )
        val ingredient9 = Ingredient(
            name = "ground cinnamon",
            quantity = 0.25,
            unit = "tsp"
        )

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
        )).thenReturn(recipe.ingredients)

        val returnedRecipe = subject.parse(recipe.originUrl)
        assertEquals(recipe, returnedRecipe)
    }

    @Test
    fun parse_getsRecipeName() {
        val recipe = testResources.recipe

        whenever(convertAndAddSameIngredientUseCase.execute(recipe.ingredients))
                .thenReturn(recipe.ingredients)

        assertEquals(recipe.name, subject.parse(recipe.originUrl).name)
    }

    @Test
    fun parse_getsRecipeIngredients() {
        val recipe = testResources.recipe
        val ingredient1 = Ingredient(
            name = "Little Potato Co. Creamer potatoes (I used Dynamic Duo)",
            quantity = 1.5,
            unit = "lb"
        )
        val ingredient2 = Ingredient(
            name = "soft goat cheese (chevre), room temperature",
            quantity = 2.0,
            unit = "oz"
        )
        val ingredient3 = Ingredient(
            name = "diced roasted red pepper",
            quantity = 3.0,
            unit = "tbsp"
        )
        val ingredient4 = Ingredient(
            name = "pitted Kalamata olives, diced",
            quantity = 4.0,
            unit = ""
        )
        val ingredient5 = Ingredient(
            name = "minced flat-leaf parsley",
            quantity = 1.0,
            unit = "tbsp"
        )
        val ingredient6 = Ingredient(
            name = "soft goat cheese (chevre), room temperature",
            quantity = 2.0,
            unit = "oz"
        )
        val ingredient7 = Ingredient(
            name = "pistachios halves, divided",
            quantity = 3.0,
            unit = "tbsp"
        )
        val ingredient8 = Ingredient(
            name = "honey",
            quantity = 1.0,
            unit = "tsp"
        )
        val ingredient9 = Ingredient(
            name = "ground cinnamon",
            quantity = 0.25,
            unit = "tsp"
        )

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

        val ingredients = subject.parse(recipe.originUrl).ingredients
        assertEquals(recipe.ingredients, ingredients)
    }

    @Test
    fun parse_getsRecipeImageUrl() {
        val recipe = testResources.recipe

        val imgUrl = subject.parse(recipe.originUrl).imgUrl
        assertEquals(recipe.imgUrl, imgUrl)
    }

    @Test
    fun parse_getsRecipesSteps() {
        val recipe = testResources.recipe

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

    @Test
    fun parse_importStepsFromBigParagraph() {
        val steps1 = subject.parse("https://www.cookincanuck.com/baked-tortellini-with-turkey-butternut-squash-chard-recipe/").steps

        val steps = asList(
                Step(name = "Preheat the oven to 350 degrees F."),
                Step(name = "Bring a large saucepan of salted water to a boil over high heat. Add the butternut squash cubes and cook until tender when pierced with a fork, about 10 minutes. Using a slotted spoon, transfer the squash to a bowl and mash with the back of a fork. Set aside."),
                Step(name = "Add the tortellini to the boiling water and cook for 2 minutes less than directed by package instructions. Drain the tortellini and transfer to a large bowl."),
                Step(name = "To the tortellini, add 1 cup tomato sauce, mashed butternut squash, turkey (or chicken) and chard. Stir to combine."),
                Step(name = "Spread ¾ cup tomato sauce on the bottom of a 9- by 12-inch (or 9- by 13 inch) baking dish."),
                Step(name = "Transfer half or the tortellini mixture to the baking dish and spread evenly. Top with ¾ cup tomato sauce."),
                Step(name = "Transfer the remaining tortellini mixture to the baking dish, spreading evenly. Spread remaining ¾ cup tomato sauce over top."),
                Step(name = "Sprinkle the topping evenly on top of the tortellini. Bake, uncovered, until the cheese is melted and the casserole is heated through, about 30 minutes. Serve."),
                Step(name = "The topping:"),
                Step(name = "In a medium bowl, stir together the Parmesan cheese, sage and pecans.")
        )

        assertEquals(steps, steps1)
    }

    @Test
    fun isUrlSupported() {
        assertThat(subject.isUrlSupported("https://www.cookincanuck.com/some-fake-recipe/")).isTrue()
    }
}