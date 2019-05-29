package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IngredientFromStringUseCaseTest {
    private lateinit var subject: IngredientFromStringUseCase

    @BeforeEach
    fun setUp() {
        subject = IngredientFromStringUseCase()
    }

    @Test
    fun execute() {
        val expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("1 cup something"))
    }

    @Test
    fun execute_transformsSpecialFractionCharacters() {
        var expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0 / 2.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("½ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0 / 3.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("⅓ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 2.0 / 3.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("⅔ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0 / 3.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("⅓ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0 / 4.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("¼ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 3.0 / 4.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("¾ cup something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0 / 8.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("⅛ cup something"))
    }

    @Test
    fun execute_convertsLongUnitToShort() {
        var expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "oz"
        )
        assertEquals(expectedIngredient, subject.execute("1 ounce something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "lb"
        )
        assertEquals(expectedIngredient, subject.execute("1 pound something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "tbsp"
        )
        assertEquals(expectedIngredient, subject.execute("1 tablespoon something"))

        expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "tsp"
        )
        assertEquals(expectedIngredient, subject.execute("1 teaspoon something"))
    }

    @Test
    fun execute_removesPluralForUnits() {
        val expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("1 cups something"))
    }

    @Test
    fun execute_acceptsNoUnit() {
        val expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.0,
            unit = ""
        )
        assertEquals(expectedIngredient, subject.execute("1 something"))
    }

    @Test
    fun execute_acceptsParenthesesInName() {
        val expectedIngredient = Ingredient(
            name = "(something)",
            quantity = 1.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("1 cup (something)"))
    }

    @Test
    fun execute_acceptsFractions() {
        val expectedIngredient = Ingredient(
            name = "something",
            quantity = 1.5,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("1 1/2 cup something"))
    }

    @Test
    fun execute_changesUnitToLowerButNotName() {
        val expectedIngredient = Ingredient(
            name = "Something",
            quantity = 1.0,
            unit = "cup"
        )
        assertEquals(expectedIngredient, subject.execute("1 Cup Something"))
    }

    @Test
    fun execute_removesPointAfterUnit() {
        val expectedIngredient = Ingredient(
            name = "Something",
            quantity = 1.0,
            unit = "lb"
        )
        assertEquals(expectedIngredient, subject.execute("1 lb. Something"))
    }

    @Test
    fun execute_handlesSpecialSlash() {
        val expectedIngredient = Ingredient(
            name = "Something",
            quantity = 0.5,
            unit = "lb"
        )
        assertEquals(expectedIngredient, subject.execute("1⁄2 lb Something"))
    }

    @Test
    fun execute_supportsGrams() {
        val expectedIngredient = Ingredient(
            name = "farine",
            quantity = 300.0,
            unit = "g"
        )
        assertEquals(expectedIngredient, subject.execute("300 g de farine"))
    }

    @Test
    fun execute_supportsCentiliters() {
        val expectedIngredient = Ingredient(
            name = "lait",
            quantity = 250.0,
            unit = "cl"
        )
        assertEquals(expectedIngredient, subject.execute("250 cl de lait"))
    }

    @Test
    fun execute_supportsLiters() {
        val expectedIngredient = Ingredient(
            name = "lait",
            quantity = 1.0,
            unit = "l"
        )
        assertEquals(expectedIngredient, subject.execute("1 l de lait"))
    }

    @Test
    fun execute_supportsCuillereASoupe() {
        var expectedIngredient = Ingredient(
            name = "sucre",
            quantity = 3.0,
            unit = "tbsp"
        )
        assertEquals(expectedIngredient, subject.execute("3 cuillères à soupe de sucre"))

        expectedIngredient = Ingredient(
            name = "sucre",
            quantity = 1.0,
            unit = "tbsp"
        )
        assertEquals(expectedIngredient, subject.execute("1 cuillère à soupe de sucre"))
    }

    @Test
    fun execute_supportsAccents() {
        val expectedIngredient = Ingredient(
            name = "verre de bière",
            quantity = 0.5
        )
        assertEquals(expectedIngredient, subject.execute("1/2 verre de bière"))
    }
}