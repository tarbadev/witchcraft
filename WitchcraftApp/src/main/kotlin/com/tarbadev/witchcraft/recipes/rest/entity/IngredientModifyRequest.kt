package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient

data class IngredientModifyRequest(
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = ""
) {
  fun toIngredient(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        quantity = getQuantity(quantity, unit)
    )
  }
}