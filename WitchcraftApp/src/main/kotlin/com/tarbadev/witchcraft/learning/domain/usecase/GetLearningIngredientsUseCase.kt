package com.tarbadev.witchcraft.learning.domain.usecase

import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import org.springframework.stereotype.Component

@Component
class GetLearningIngredientsUseCase(private val learningIngredientRepository: LearningIngredientRepository) {
  fun execute(): List<LearningIngredient> {
    return learningIngredientRepository.findAll()
  }
}
