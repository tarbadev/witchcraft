package com.tarbadev.witchcraft.learning.persistence.entity

import com.tarbadev.witchcraft.converter.centiliter
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.entity.Language
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LearningIngredientEntityTest {
  @Test
  fun toIngredientToValidate() {
    val learningIngredientEntity = LearningIngredientEntity(
        12,
        "another ingredient",
        "to taste",
        350.0,
        "cl",
        "Another ingredient line",
        Language.FRENCH.toString(),
        false
    )
    val learningIngredient = LearningIngredient(
        id = 12,
        line = "Another ingredient line",
        quantity = 350.centiliter,
        name = "another ingredient",
        detail = "to taste",
        language = Language.FRENCH,
        valid = false
    )

    assertThat(learningIngredientEntity.toLearningIngredient()).isEqualTo(learningIngredient)
  }

  @Test
  fun fromIngredientToValidate() {
    val learningIngredientEntity = LearningIngredientEntity(
        12,
        "another ingredient",
        "to taste",
        350.0,
        "cl",
        "Another ingredient line",
        Language.FRENCH.toString(),
        false
    )
    val learningIngredient = LearningIngredient(
        id = 12,
        line = "Another ingredient line",
        quantity = 350.centiliter,
        name = "another ingredient",
        detail = "to taste",
        language = Language.FRENCH,
        valid = false
    )

    assertThat(LearningIngredientEntity.fromLearningIngredient(learningIngredient)).isEqualTo(learningIngredientEntity)
  }
}