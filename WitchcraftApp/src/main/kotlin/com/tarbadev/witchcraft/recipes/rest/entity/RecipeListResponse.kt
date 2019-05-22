package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class RecipeListResponse(
    val recipes: List<Recipe> = emptyList()
)