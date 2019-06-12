package com.tarbadev.witchcraft.carts.domain.entity

import com.tarbadev.witchcraft.converter.getUnitShortName
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient

data class Item(
    val id: Int = 0,
    val name: String,
    val quantity: Double,
    val unit: String,
    val enabled: Boolean
) {
  companion object {
    fun fromIngredient(ingredient: Ingredient): Item {
      return Item(
          0,
          ingredient.name,
          ingredient.quantity.getValue().toDouble(),
          getUnitShortName(ingredient.quantity),
          true
      )
    }
  }
}
