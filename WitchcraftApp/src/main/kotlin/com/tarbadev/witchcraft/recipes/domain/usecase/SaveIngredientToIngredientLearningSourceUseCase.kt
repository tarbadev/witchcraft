package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.springframework.stereotype.Component

@Component
class SaveIngredientToIngredientLearningSourceUseCase(private val learningIngredientRepository: LearningIngredientRepository) {
  fun execute(line: String, ingredient: Ingredient) {
    learningIngredientRepository.save(
        LearningIngredient(
            id = 0,
            line = line,
            name = ingredient.name,
            detail = "",
            quantity = ingredient.quantity,
            language = Language.ENGLISH,
            valid = false
        )
    )
  }
}
