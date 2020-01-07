package com.tarbadev.witchcraft.recipes.domain.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient

interface IngredientRepository {
  fun save(ingredient: Ingredient): Ingredient
}
