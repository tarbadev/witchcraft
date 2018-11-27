package com.tarbadev.witchcraft.recipes.domain.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

interface RecipeRepository {
    fun saveRecipe(recipe: Recipe): Recipe

    fun updateRecipe(recipe: Recipe): Recipe

    fun findAll(): List<Recipe>

    fun findById(id: Int): Recipe?

    fun delete(id: Int)

    fun setFavorite(id: Int, isFavorite: Boolean): Recipe

    fun findAllFavorite(): List<Recipe>

    fun findLastAddedRecipes(): List<Recipe>

    fun existsById(id: Int): Boolean
}
