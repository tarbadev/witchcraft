package com.tarbadev.witchcraft.recipes.rest.entity

data class RecipeFormRequest(
    var name: String = "",
    var url: String = "",
    var imageUrl: String = "",
    var ingredients: String = "",
    var steps: String = "",
    var portions: Int = 0
)
