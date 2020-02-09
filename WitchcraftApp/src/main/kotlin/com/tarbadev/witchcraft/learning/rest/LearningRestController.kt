package com.tarbadev.witchcraft.learning.rest

import com.tarbadev.witchcraft.learning.domain.usecase.GetLearningIngredientsUseCase
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/learning")
class LearningRestController(
    private val getLearningIngredientsUseCase: GetLearningIngredientsUseCase
) {

  @GetMapping
  fun list(): List<LearningIngredientResponse> {
    return getLearningIngredientsUseCase.execute().map { LearningIngredientResponse.fromLearningIngredient(it) }
  }
}