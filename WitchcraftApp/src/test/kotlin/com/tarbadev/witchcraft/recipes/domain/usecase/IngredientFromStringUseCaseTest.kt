package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.converter.*
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.assertj.core.api.Assertions.assertThat
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
        quantity = 1.cup
    )

    assertThat(subject.execute("1 cup something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_transformsSpecialFractionCharacters() {
    var expectedIngredient = Ingredient(
        name = "something",
        quantity = (1.0 / 2.0).cup
    )
    assertThat(subject.execute("½ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (1.0 / 3.0).cup
    )
    assertThat(subject.execute("⅓ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (2.0 / 3.0).cup
    )
    assertThat(subject.execute("⅔ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (1.0 / 3.0).cup
    )
    assertThat(subject.execute("⅓ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (1.0 / 4.0).cup
    )
    assertThat(subject.execute("¼ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (3.0 / 4.0).cup
    )
    assertThat(subject.execute("¾ cup something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = (1.0 / 8.0).cup
    )
    assertThat(subject.execute("⅛ cup something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_convertsLongUnitToShort() {
    var expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.ounce
    )
    assertThat(subject.execute("1 ounce something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.pound
    )
    assertThat(subject.execute("1 pound something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.tablespoon
    )
    assertThat(subject.execute("1 tablespoon something")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.teaspoon
    )
    assertThat(subject.execute("1 teaspoon something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_removesPluralForUnits() {
    val expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.cup
    )
    assertThat(subject.execute("1 cups something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsNoUnit() {
    val expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.unit
    )
    assertThat(subject.execute("1 something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsParenthesesInName() {
    val expectedIngredient = Ingredient(
        name = "ingredient (something)",
        quantity = 1.cup
    )
    assertThat(subject.execute("1 cup ingredient (something)")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsFractions() {
    val expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.5.cup
    )
    assertThat(subject.execute("1 1/2 cup something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_changesTextToLowercase() {
    val expectedIngredient = Ingredient(
        name = "diced roasted red pepper",
        quantity = 1.cup
    )
    assertThat(subject.execute("1 Cup diced ROASTED RED PEPPER")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_removesPointAfterUnit() {
    val expectedIngredient = Ingredient(
        name = "something",
        quantity = 1.pound
    )
    assertThat(subject.execute("1 lb. Something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_handlesSpecialSlash() {
    val expectedIngredient = Ingredient(
        name = "something",
        quantity = 0.5.pound
    )
    assertThat(subject.execute("1⁄2 lb Something")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_supportsGrams() {
    val expectedIngredient = Ingredient(
        name = "farine",
        quantity = 300.gram
    )
    assertThat(subject.execute("300 g de farine")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_supportsCentiliters() {
    val expectedIngredient = Ingredient(
        name = "lait",
        quantity = 250.centiliter
    )
    assertThat(subject.execute("250 cl de lait")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_supportsLiters() {
    val expectedIngredient = Ingredient(
        name = "lait",
        quantity = 1.liter
    )
    assertThat(subject.execute("1 l de lait")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_supportsCuillereASoupe() {
    var expectedIngredient = Ingredient(
        name = "sucre",
        quantity = 3.tablespoon
    )
    assertThat(subject.execute("3 cuillères à soupe de sucre")).isEqualTo(expectedIngredient)

    expectedIngredient = Ingredient(
        name = "sucre",
        quantity = 1.tablespoon
    )
    assertThat(subject.execute("1 cuillère à soupe de sucre")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_supportsAccents() {
    val expectedIngredient = Ingredient(
        name = "bière",
        quantity = 0.5.verre
    )
    assertThat(subject.execute("1/2 verre de bière")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_trimsSpacesFromName() {
    val expectedIngredient = Ingredient(
        name = "honey",
        quantity = 3.teaspoon
    )
    assertThat(subject.execute("3 tsp honey")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_canDetectWhenNoUnit() {
    val expectedIngredient = Ingredient(
        name = "oeufs entiers",
        quantity = 3.unit
    )
    assertThat(subject.execute("3 oeufs entiers")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_removesContainsMilk() {
    val expectedIngredient = Ingredient(
        name = "mozzarella",
        quantity = 0.5.teaspoon
    )
    assertThat(subject.execute("1/2 tsp mozzarella(ContainsMilk)")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_removesContainsWheat() {
    val expectedIngredient = Ingredient(
        name = "mozzarella",
        quantity = 0.5.teaspoon
    )
    assertThat(subject.execute("1/2 tsp mozzarella(ContainsWheat)")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsTheWordUnitAsAUnit() {
    val expectedIngredient = Ingredient(
        name = "flour tortilla",
        quantity = 1.unit
    )
    assertThat(subject.execute("1 unit Flour Tortilla")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsNoQuantityNorUnit() {
    val expectedIngredient = Ingredient(
        name = "flour tortilla",
        quantity = 1.unit
    )
    assertThat(subject.execute("Flour Tortilla")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsQuantityAndUnitAtTheEnd() {
    val expectedIngredient = Ingredient(
        name = "salt",
        quantity = 1.teaspoon
    )
    assertThat(subject.execute("salt 1 tsp")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsQuantityUnitWithoutSpaceAtTheEnd() {
    val expectedIngredient = Ingredient(
        name = "salt",
        quantity = 1.teaspoon
    )
    assertThat(subject.execute("salt 1tsp")).isEqualTo(expectedIngredient)
  }

  @Test
  fun execute_acceptsQuantityUnitWithoutSpaceAtTheBeginning() {
    val expectedIngredient = Ingredient(
        name = "salt",
        quantity = 1.teaspoon
    )
    assertThat(subject.execute("1tsp salt")).isEqualTo(expectedIngredient)
  }
}