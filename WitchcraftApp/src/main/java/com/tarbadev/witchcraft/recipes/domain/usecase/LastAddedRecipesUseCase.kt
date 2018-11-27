package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class LastAddedRecipesUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(): List<Recipe> {
        return recipeRepository.findLastAddedRecipes()
    }
}
