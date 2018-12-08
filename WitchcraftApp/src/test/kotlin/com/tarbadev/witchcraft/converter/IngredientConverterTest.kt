package com.tarbadev.witchcraft.converter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class IngredientConverterTest {
    private val unitConverter: UnitConverter = mock()
    private var subject: IngredientConverter = IngredientConverter(unitConverter)

    @BeforeEach
    fun setUp() {
        reset(unitConverter)
    }

    @Test
    fun addToHighestUnit() {
        val ingredientOz = Ingredient(quantity = 10.0, unit = UnitConverter.UnitName.OZ.name)
        val ingredientMl = Ingredient(quantity = 10.0, unit = UnitConverter.UnitName.ML.name)
        val expectedIngredient = Ingredient(unit = UnitConverter.UnitName.OZ.name, quantity = 10.33814)

        whenever(unitConverter.convertToHighestUnit(ingredientMl.quantity, ingredientMl.unit, ingredientOz.unit))
            .thenReturn(AbstractMap.SimpleEntry(UnitConverter.UnitName.OZ.name, expectedIngredient.quantity - ingredientOz.quantity))

        assertEquals(expectedIngredient, subject.addToHighestUnit(ingredientMl, ingredientOz))
    }

    @Test
    fun addToHighestUnit_OzToMl() {
        val ingredientOz = Ingredient(quantity = 10.0, unit = UnitConverter.UnitName.OZ.name)
        val ingredientMl = Ingredient(quantity = 10.0, unit = UnitConverter.UnitName.ML.name)
        val expectedIngredient = Ingredient(unit = UnitConverter.UnitName.OZ.name, quantity = 10.33814)

        whenever(unitConverter.convertToHighestUnit(ingredientOz.quantity, ingredientOz.unit, ingredientMl.unit))
            .thenReturn(AbstractMap.SimpleEntry(UnitConverter.UnitName.OZ.name, ingredientOz.quantity))
        whenever(unitConverter.convert(ingredientMl.quantity, ingredientMl.unit, ingredientOz.unit))
            .thenReturn(expectedIngredient.quantity - ingredientOz.quantity)

        assertEquals(expectedIngredient, subject.addToHighestUnit(ingredientOz, ingredientMl))
    }
}