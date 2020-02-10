package com.tarbadev.witchcraft.learning.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.usecase.GetLearningIngredientsUseCase
import com.tarbadev.witchcraft.learning.domain.usecase.ValidateLearningIngredientUseCase
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientRequest
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(LearningRestController::class)
class LearningRestControllerTest(@Autowired private val mockMvc: MockMvc) {

  @MockBean
  private lateinit var getLearningIngredientsUseCase: GetLearningIngredientsUseCase

  @MockBean
  private lateinit var validateLearningIngredientUseCase: ValidateLearningIngredientUseCase

  @Test
  fun `list() returns the ingredients to validate`() {
    val ingredientsToValidate = listOf(
        LearningIngredient(
            12,
            "Some ingredient line",
            "some ingredient",
            "",
            2.teaspoon,
            Language.ENGLISH,
            true
        )
    )
    val ingredientsResponse = ingredientsToValidate.map { LearningIngredientResponse.fromLearningIngredient(it) }

    whenever(getLearningIngredientsUseCase.execute()).thenReturn(ingredientsToValidate)

    mockMvc.perform(get("/api/learning"))
        .andExpect(status().isOk)
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(ingredientsResponse)))
  }

  @Test
  fun `validate() calls the validateLearningIngredientUseCase`() {
    val learningIngredientRequest = LearningIngredientRequest(
        name = "some ingredient",
        quantity = 23.0,
        unit = "tsp",
        language = "FRENCH",
        detail = "to taste"
    )

    mockMvc.perform(patch("/api/learning/21")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(learningIngredientRequest))
    )
        .andExpect(status().isNoContent)

    verify(validateLearningIngredientUseCase).execute(21, "some ingredient", 23.teaspoon, Language.FRENCH, "to taste")
  }
}