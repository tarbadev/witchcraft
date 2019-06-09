package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class AddExpressRecipeUseCase(private val recipeRepository: RecipeRepository) {
  fun execute(recipe: Recipe): Recipe {
    return recipeRepository.save(recipe)
  }
}
