package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.junit.jupiter.api.Test

class SaveIngredientToIngredientLearningSourceUseCaseTest {
  private val learningIngredientRepository: LearningIngredientRepository = mock()
  private val saveIngredientToIngredientLearningSourceUseCase = SaveIngredientToIngredientLearningSourceUseCase(learningIngredientRepository)

  @Test
  fun execute_transformsIngredientAndSavesItInDatabase() {
    val line = "2tsp Salt"
    val ingredient = Ingredient(name = "salt", quantity = 2.teaspoon)
    val learningIngredient = LearningIngredient(
        line = line,
        name = ingredient.name,
        detail = "",
        quantity = ingredient.quantity,
        language = Language.ENGLISH,
        valid = false
    )
    saveIngredientToIngredientLearningSourceUseCase.execute(line, ingredient)

    verify(learningIngredientRepository).save(learningIngredient)
  }
}