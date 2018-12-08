package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class SetFavoriteRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(id: Int, favorite: Boolean): Recipe {
        return recipeRepository.setFavorite(id, favorite)
    }
}
