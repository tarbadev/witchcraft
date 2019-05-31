package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.GetRecipeDetailsFromFormUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.GetRecipeDetailsFromUrlUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.SaveRecipeUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeFormRequest
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/recipes")
class NewRecipeRestController(
    private val getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase
) {
    @PostMapping("/import-from-url")
    fun importFromUrl(@RequestBody recipeFormRequest: RecipeFormRequest): RecipeResponse {
        val recipe = getRecipeDetailsFromUrlUseCase.execute(recipeFormRequest.url)

        return RecipeResponse.fromRecipe(saveRecipeUseCase.execute(recipe))
    }

    @PostMapping("/import-from-form")
    fun importFromForm(@RequestBody recipeFormRequest: RecipeFormRequest): RecipeResponse {
        val recipe = getRecipeDetailsFromFormUseCase.execute(
            recipeFormRequest.name,
            recipeFormRequest.url,
            recipeFormRequest.ingredients,
            recipeFormRequest.steps,
            recipeFormRequest.imageUrl
        )

        return RecipeResponse.fromRecipe(saveRecipeUseCase.execute(recipe))
    }
}
