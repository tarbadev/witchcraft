package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.repository.IngredientRepository
import org.springframework.stereotype.Component

@Component
class DeleteIngredientUseCase(private val ingredientRepository: IngredientRepository) {
  fun execute(id: Int) {
    ingredientRepository.delete(id)
  }
}
