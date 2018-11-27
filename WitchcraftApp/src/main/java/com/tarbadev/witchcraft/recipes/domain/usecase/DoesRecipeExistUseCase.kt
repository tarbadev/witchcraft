package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class DoesRecipeExistUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(id: Int): Boolean {
        return recipeRepository.existsById(id)
    }
}
