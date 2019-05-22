package com.tarbadev.witchcraft.recipes.domain.entity

data class Notes(
    val id: Int = 0,
    val recipeId: Int,
    val comment: String
)
