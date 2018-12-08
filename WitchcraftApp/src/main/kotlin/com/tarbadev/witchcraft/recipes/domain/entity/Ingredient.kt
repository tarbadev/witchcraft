package com.tarbadev.witchcraft.recipes.domain.entity

data class Ingredient (
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = ""
) {

    fun addQuantity(quantity: Double): Ingredient {
        return this.copy(quantity = this.quantity + quantity)
    }
}
