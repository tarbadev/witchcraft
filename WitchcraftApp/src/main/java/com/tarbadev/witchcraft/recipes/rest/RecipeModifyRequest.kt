package com.tarbadev.witchcraft.recipes.rest

data class RecipeModifyRequest(
    val id: Int = 0,
    val url: String = "",
    val name: String = "",
    val imgUrl: String = "",
    val favorite: Boolean = false,
    val ingredients: List<IngredientModifyRequest> = emptyList(),
    val steps: List<StepModifyRequest> = emptyList()
)
