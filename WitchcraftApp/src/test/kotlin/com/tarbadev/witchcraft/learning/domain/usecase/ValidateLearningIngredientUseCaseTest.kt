package com.tarbadev.witchcraft.learning.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import org.junit.jupiter.api.Test

class ValidateLearningIngredientUseCaseTest {
  private val learningIngredientRepository: LearningIngredientRepository = mock()
  private val validateLearningIngredientUseCase = ValidateLearningIngredientUseCase(learningIngredientRepository)

  @Test
  fun execute() {
    val id = 654
    val learningIngredient = LearningIngredient(
        id,
        "2 tsp Some ingredient line",
        "some ingredient line",
        2.teaspoon,
        Language.ENGLISH,
        false
    )
    val updatedLearningIngredient = LearningIngredient(
        id,
        "2 tsp Some ingredient line",
        "some ingredient",
        23.teaspoon,
        Language.FRENCH,
        true
    )

    whenever(learningIngredientRepository.findById(any())).thenReturn(learningIngredient)

    validateLearningIngredientUseCase.execute(
        id,
        updatedLearningIngredient.name,
        updatedLearningIngredient.quantity,
        updatedLearningIngredient.language
    )

    verify(learningIngredientRepository).findById(id)
    verify(learningIngredientRepository).save(updatedLearningIngredient)
  }
}