package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.repository.IngredientRepository
import org.springframework.stereotype.Component

@Component
class SaveIngredientUseCase(private val ingredientRepository: IngredientRepository) {
  fun execute(ingredient: Ingredient): Ingredient {
    return ingredientRepository.save(ingredient)
  }
}
