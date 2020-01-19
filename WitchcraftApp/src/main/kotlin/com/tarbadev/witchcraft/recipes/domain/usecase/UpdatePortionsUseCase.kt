package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class UpdatePortionsUseCase(private val recipeRepository: RecipeRepository) {
  fun execute(recipeId: Int, newPortions: Int): Recipe {
    val recipe = recipeRepository.findById(recipeId)!!
    return recipeRepository.save(recipe.copy(portions = newPortions))
  }
}
