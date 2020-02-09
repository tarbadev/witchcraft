package com.tarbadev.witchcraft.learning.rest.entity

import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.entity.Language
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LearningIngredientResponseTest {
  @Test
  fun fromIngredientToValidate() {
    val ingredientToValidate = LearningIngredient(
        12,
        "Some ingredient line",
        "some ingredient",
        2.teaspoon,
        Language.ENGLISH,
        true
    )
    val ingredientToValidateResponse = LearningIngredientResponse(
        12,
        "Some ingredient line",
        "some ingredient",
        2.0,
        "tsp",
        Language.ENGLISH.toString(),
        true
    )

    assertThat(LearningIngredientResponse.fromLearningIngredient(ingredientToValidate))
        .isEqualTo(ingredientToValidateResponse)
  }
}