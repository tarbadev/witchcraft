package com.tarbadev.witchcraft.recipes.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.converter.UnitHelper
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.usecase.SaveIngredientUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.IngredientModifyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(IngredientRestController::class)
class IngredientRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
  @MockBean
  private lateinit var saveIngredientUseCase: SaveIngredientUseCase

  @Test
  fun update() {
    val ingredientModifyRequest = IngredientModifyRequest(
        id = 12,
        name = "Recipe Name",
        quantity = 3.0,
        unit = "tbsp"
    )

    val ingredient = Ingredient(
        id = ingredientModifyRequest.id,
        name = ingredientModifyRequest.name,
        quantity = UnitHelper.getQuantity(ingredientModifyRequest.quantity, ingredientModifyRequest.unit)
    )

    whenever(saveIngredientUseCase.execute(any<Ingredient>())).thenReturn(ingredient)

    mockMvc.perform(MockMvcRequestBuilders.put("/api/recipes/23/ingredients/${ingredientModifyRequest.id}")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(ingredientModifyRequest))
    )
        .andExpect(MockMvcResultMatchers.status().isOk)

    verify(saveIngredientUseCase).execute(ingredient)
  }
}