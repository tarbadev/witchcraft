package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient

data class IngredientResponse(
    val id: Int,
    val name: String,
    val quantity: Double,
    val unit: String
) {
  companion object {
    fun fromIngredient(ingredient: Ingredient): IngredientResponse =
        IngredientResponse(
            id = ingredient.id,
            name = ingredient.name,
            quantity = "%.3f".format(ingredient.quantity).toDouble(),
            unit = ingredient.unit
        )
  }
}
