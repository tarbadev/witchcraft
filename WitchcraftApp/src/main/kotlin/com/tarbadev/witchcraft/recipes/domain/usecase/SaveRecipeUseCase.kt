package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class SaveRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(recipe: Recipe): Recipe {
        return if (recipe.id > 0)
            recipeRepository.updateRecipe(recipe)
        else
            recipeRepository.saveRecipe(recipe)
    }
}
