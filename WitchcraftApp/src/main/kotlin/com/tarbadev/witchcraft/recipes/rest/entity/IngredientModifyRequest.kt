package com.tarbadev.witchcraft.recipes.rest.entity

data class IngredientModifyRequest (
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = ""
)