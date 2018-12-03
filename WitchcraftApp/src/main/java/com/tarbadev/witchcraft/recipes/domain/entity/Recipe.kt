package com.tarbadev.witchcraft.recipes.domain.entity

data class Recipe (
    val id: Int = 0,
    val url: String = "",
    val originUrl: String = "",
    val name: String = "",
    val imgUrl: String = "",
    val favorite: Boolean = false,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<Step> = emptyList(),
    val isArchived: Boolean = false
)
