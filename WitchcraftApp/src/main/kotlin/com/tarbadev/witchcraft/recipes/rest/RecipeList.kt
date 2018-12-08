package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class RecipeList(
    val recipes: List<Recipe> = emptyList()
)