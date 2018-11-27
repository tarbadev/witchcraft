package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.IngredientConverter
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import java.util.*

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
        val allIngredients = Arrays.asList(
            Ingredient(name = "olive oil", quantity = 0.5, unit = "cup"),
            Ingredient(name = "olive oil", quantity = 3.0, unit = "tbsp")
        )

        val expectedIngredient = Ingredient(
            name = "olive oil",
            quantity = 0.5625,
            unit = "cup"
        )

        whenever(ingredientConverter.addToHighestUnit(allIngredients[0], allIngredients[1]))
            .thenReturn(expectedIngredient)

        assertEquals(listOf(expectedIngredient), subject.execute(allIngredients))
    }
}