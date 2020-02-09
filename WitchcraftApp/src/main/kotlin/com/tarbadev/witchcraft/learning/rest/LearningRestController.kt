package com.tarbadev.witchcraft.learning.rest

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.usecase.GetLearningIngredientsUseCase
import com.tarbadev.witchcraft.learning.domain.usecase.ValidateLearningIngredientUseCase
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientRequest
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/learning")
class LearningRestController(
    private val getLearningIngredientsUseCase: GetLearningIngredientsUseCase,
    private val validateLearningIngredientUseCase: ValidateLearningIngredientUseCase
) {

  @GetMapping
  fun list(): List<LearningIngredientResponse> {
    return getLearningIngredientsUseCase.execute().map { LearningIngredientResponse.fromLearningIngredient(it) }
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun validate(@PathVariable id: Int, @RequestBody learningIngredientRequest: LearningIngredientRequest) {
    validateLearningIngredientUseCase.execute(
        id,
        learningIngredientRequest.name,
        getQuantity(learningIngredientRequest.quantity, learningIngredientRequest.unit),
        Language.valueOf(learningIngredientRequest.language)
    )
  }
}