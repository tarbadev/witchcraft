package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.DeleteIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.SaveIngredientUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.IngredientModifyRequest
import com.tarbadev.witchcraft.recipes.rest.entity.IngredientResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/recipes/{recipeId}/ingredients/{ingredientId}")
@RestController
class IngredientRestController(
    private val saveIngredientUseCase: SaveIngredientUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase
) {
  @PutMapping
  fun update(
      @RequestBody ingredientModifyRequest: IngredientModifyRequest
  ): IngredientResponse {
    return IngredientResponse.fromIngredient(
        saveIngredientUseCase.execute(ingredientModifyRequest.toIngredient())
    )
  }

  @DeleteMapping
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  fun delete(@PathVariable ingredientId: Int) {
    deleteIngredientUseCase.execute(ingredientId)
  }
}