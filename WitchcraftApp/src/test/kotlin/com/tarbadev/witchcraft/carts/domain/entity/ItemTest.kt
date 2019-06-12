package com.tarbadev.witchcraft.carts.domain.entity

import com.tarbadev.witchcraft.converter.pound
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ItemTest {
  @Test
  fun fromIngredient() {
    val ingredient = Ingredient(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.pound
    )

    val item = Item(
        id = 0,
        name = "Raclette cheese",
        quantity = 2.0,
        unit = "lb",
        enabled = true
    )

    Assertions.assertThat(Item.fromIngredient(ingredient)).isEqualTo(item)
  }
}