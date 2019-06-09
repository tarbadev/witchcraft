package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.*
import com.tarbadev.witchcraft.recipes.rest.entity.ExpressRecipeRequest
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeFormRequest
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import com.tarbadev.witchcraft.recipes.rest.entity.SupportedDomainResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class NewRecipeRestController(
    private val getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase,
    private val getSupportedDomainsUseCase: GetSupportedDomainsUseCase,
    private val addExpressRecipeUseCase: AddExpressRecipeUseCase
) {
  @PostMapping("/import-from-url")
  fun importFromUrl(@RequestBody recipeFormRequest: RecipeFormRequest): RecipeResponse {
    val recipe = getRecipeDetailsFromUrlUseCase.execute(recipeFormRequest.url)

    return RecipeResponse.fromRecipe(saveRecipeUseCase.execute(recipe))
  }

  @GetMapping("/import-from-url/supported")
  fun getSupportedDomains(): List<SupportedDomainResponse> {
    return getSupportedDomainsUseCase.execute().map { SupportedDomainResponse.fromSupportedDomain(it) }
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

  @PostMapping("/express")
  fun addExpressRecipe(@RequestBody expressRecipeRequest: ExpressRecipeRequest): RecipeResponse {
    return RecipeResponse.fromRecipe(addExpressRecipeUseCase.execute(expressRecipeRequest.toRecipe()))
  }
}
