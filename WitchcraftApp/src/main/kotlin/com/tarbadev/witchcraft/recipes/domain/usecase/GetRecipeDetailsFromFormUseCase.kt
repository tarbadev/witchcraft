package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.springframework.stereotype.Component

@Component
class GetRecipeDetailsFromFormUseCase(
    private val ingredientFromStringUseCase: IngredientFromStringUseCase,
    private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) {

  fun execute(name: String, url: String, ingredients: String, steps: String, imgUrl: String): Recipe {
    return Recipe(
        name = name,
        originUrl = url,
        imgUrl = imgUrl,
        ingredients = getIngredientsFromString(ingredients),
        steps = steps
            .split("\n".toRegex())
            .dropLastWhile { it.isEmpty() }
            .map { Step(name = it) },
        portions = null
    )
  }

  private fun getIngredientsFromString(ingredientsString: String): List<Ingredient> {
    return convertAndAddSameIngredientUseCase.execute(
        ingredientsString.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            .map { ingredientFromStringUseCase.execute(it)!! }
    )
  }
}
