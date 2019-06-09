package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class ExpressRecipeRequest(val name: String) {
  fun toRecipe() = Recipe(name = name)
}
