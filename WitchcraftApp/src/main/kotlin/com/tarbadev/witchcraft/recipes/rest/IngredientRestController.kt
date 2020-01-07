package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.SaveIngredientUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.IngredientModifyRequest
import com.tarbadev.witchcraft.recipes.rest.entity.IngredientResponse
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/recipes/{recipeId}/ingredients/{ingredientId}")
@RestController
class IngredientRestController(
    private val saveIngredientUseCase: SaveIngredientUseCase
) {
  @PutMapping
  fun update(
      @RequestBody ingredientModifyRequest: IngredientModifyRequest
  ): IngredientResponse {
    return IngredientResponse.fromIngredient(
        saveIngredientUseCase.execute(ingredientModifyRequest.toIngredient())
    )
  }
}