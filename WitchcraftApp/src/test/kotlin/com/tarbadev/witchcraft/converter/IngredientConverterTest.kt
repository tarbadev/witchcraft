package com.tarbadev.witchcraft.converter

import com.tarbadev.witchcraft.converter.IngredientConverter.IncompatibleIngredientUnitException
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class IngredientConverterTest {
  private var subject: IngredientConverter = IngredientConverter()

  @Test
  fun addToHighestUnit_CompatibleUnits() {
    val ingredientMl = Ingredient(quantity = 10.milliliter)
    val ingredientOz = Ingredient(quantity = 10.fluid_ounce)
    val expectedIngredient = Ingredient(quantity = 10.33814022701843.fluid_ounce)

    assertThat(subject.addToHighestUnit(ingredientMl, ingredientOz)).isEqualTo(expectedIngredient)
  }

  @Test
  fun addToHighestUnit_IncompatibleUnits_OuncesForIngredient1() {
    val ingredientOz = Ingredient(quantity = 10.ounce)
    val ingredientMl = Ingredient(quantity = 10.milliliter)
    val expectedIngredient = Ingredient(quantity = 10.33814022701843.fluid_ounce)

    assertThat(subject.addToHighestUnit(ingredientOz, ingredientMl)).isEqualTo(expectedIngredient)
  }

  @Test
  fun addToHighestUnit_IncompatibleUnits_OuncesForIngredient2() {
    val ingredientOz = Ingredient(quantity = 10.ounce)
    val ingredientMl = Ingredient(quantity = 10.milliliter)
    val expectedIngredient = Ingredient(quantity = 10.33814022701843.fluid_ounce)

    assertThat(subject.addToHighestUnit(ingredientMl, ingredientOz)).isEqualTo(expectedIngredient)
  }

  @Test
  fun addToHighestUnit_IncompatibleUnits_ThrowsWhenDoesNotKnowWhatToDo() {
    val ingredientOz = Ingredient(quantity = 10.gram)
    val ingredientMl = Ingredient(quantity = 10.milliliter)
    val expectedException = IncompatibleIngredientUnitException(ingredientMl, ingredientOz)

    assertThatThrownBy { subject.addToHighestUnit(ingredientMl, ingredientOz) }.isEqualTo(expectedException)
  }
}