package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class DeleteRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(id: Int) {
        recipeRepository.delete(id)
    }
}
