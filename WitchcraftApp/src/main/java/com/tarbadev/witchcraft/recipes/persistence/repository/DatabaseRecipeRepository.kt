package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity
import org.springframework.stereotype.Component

@Component
class DatabaseRecipeRepository(private val recipeEntityRepository: RecipeEntityRepository) : RecipeRepository {

    override fun saveRecipe(recipe: Recipe): Recipe {
        return recipeEntityRepository.saveAndFlush(RecipeEntity(recipe)).recipe()
    }

    override fun updateRecipe(recipe: Recipe): Recipe {
        val entity = recipeEntityRepository.findById(recipe.id).orElse(null).copy(
            name = recipe.name,
            originUrl = recipe.originUrl,
            imgUrl = recipe.imgUrl,
            ingredients = recipe.ingredients.map { IngredientEntity(it) },
            steps = recipe.steps.map { StepEntity(it) }.toSet()
        )
        return recipeEntityRepository.saveAndFlush(entity).recipe()
    }

    override fun findAll(): List<Recipe> {
        return recipeEntityRepository.findAllByOrderByName()
            .filter { !it.isArchived }
            .map { it.recipe() }
    }

    override fun findById(id: Int): Recipe? {
        return recipeEntityRepository.findById(id)
            .filter { !it.isArchived }
            .map { it.recipe() }
            .orElse(null)
    }

    override fun delete(id: Int) {
        recipeEntityRepository.findById(id).get().isArchived = true
        recipeEntityRepository.flush()
    }

    override fun setFavorite(id: Int, isFavorite: Boolean): Recipe {
        val recipeEntity = recipeEntityRepository.findById(id).get()
        recipeEntity.favorite = isFavorite

        recipeEntityRepository.flush()

        return recipeEntity.recipe()
    }

    override fun findAllFavorite(): List<Recipe> {
        return recipeEntityRepository.findAllByFavorite(true)
            .filter { !it.isArchived }
            .map { it.recipe() }
    }

    override fun findLastAddedRecipes(): List<Recipe> {
        return recipeEntityRepository.findTop8ByOrderByIdDesc()
            .filter { !it.isArchived }
            .map { it.recipe() }
    }

    override fun existsById(id: Int): Boolean {
        return recipeEntityRepository.existsById(id)
    }
}
