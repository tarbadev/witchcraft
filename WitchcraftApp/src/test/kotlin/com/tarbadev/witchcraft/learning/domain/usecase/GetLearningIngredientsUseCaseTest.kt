package com.tarbadev.witchcraft.learning.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetLearningIngredientsUseCaseTest {
  private val learningIngredientRepository: LearningIngredientRepository = mock()
  private val getIngredientsToValidateUseCase = GetLearningIngredientsUseCase(learningIngredientRepository)

  @Test
  fun execute() {
    val ingredientsToValidate = listOf(
        LearningIngredient(
            12,
            "Some ingredient line",
            "some ingredient",
            "to taste",
            2.teaspoon,
            Language.ENGLISH,
            true
        )
    )

    whenever(learningIngredientRepository.findAll()).thenReturn(ingredientsToValidate)

    assertThat(getIngredientsToValidateUseCase.execute()).isEqualTo(ingredientsToValidate)
  }
}