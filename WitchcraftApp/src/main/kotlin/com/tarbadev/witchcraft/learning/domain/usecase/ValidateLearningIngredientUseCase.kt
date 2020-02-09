package com.tarbadev.witchcraft.learning.domain.usecase

import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import org.springframework.stereotype.Component
import tech.units.indriya.ComparableQuantity
import javax.measure.quantity.Volume

@Component
class ValidateLearningIngredientUseCase(private val learningIngredientRepository: LearningIngredientRepository) {
  fun execute(id: Int, name: String, quantity: ComparableQuantity<*>, language: Language) {
    val learningIngredient = learningIngredientRepository.findById(id)
    learningIngredientRepository.save(learningIngredient!!.copy(
        name = name,
        quantity = quantity,
        language = language,
        valid = true
    ))
  }
}
