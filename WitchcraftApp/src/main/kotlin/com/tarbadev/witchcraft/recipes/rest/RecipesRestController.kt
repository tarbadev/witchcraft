package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.*
import com.tarbadev.witchcraft.recipes.rest.entity.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipesRestController(
    private val recipeCatalogUseCase: RecipeCatalogUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val doesRecipeExistUseCase: DoesRecipeExistUseCase,
    private val setFavoriteRecipeUseCase: SetFavoriteRecipeUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val lastAddedRecipesUseCase: LastAddedRecipesUseCase,
    private val editStepNoteUseCase: EditStepNoteUseCase,
    private val updatePortionsUseCase: UpdatePortionsUseCase
) {
  @GetMapping
  fun list(): RecipeListResponse {
    val recipes = recipeCatalogUseCase.execute()

    return RecipeListResponse.fromRecipeList(recipes)
  }

  @GetMapping("/{id}")
  fun show(@PathVariable("id") id: Int): RecipeResponse? {
    val recipe = getRecipeUseCase.execute(id) ?: return null

    return RecipeResponse.fromRecipe(recipe)
  }

  @PatchMapping("/{id}")
  fun setFavorite(
      @PathVariable("id") id: Int,
      @RequestBody requestParams: Map<String, String>
  ): RecipeResponse {
    val favorite = java.lang.Boolean.parseBoolean(requestParams["favorite"])
    return RecipeResponse.fromRecipe(setFavoriteRecipeUseCase.execute(id, favorite))
  }

  @DeleteMapping("/{id}")
  fun deleteRecipe(@PathVariable id: Int): ResponseEntity<*> {
    if (!doesRecipeExistUseCase.execute(id)) {
      return ResponseEntity.notFound().build<Any>()
    }

    deleteRecipeUseCase.execute(id)

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Any>()
  }

  @PutMapping("/{id}/update")
  fun update(@RequestBody recipeModifyRequest: RecipeModifyRequest): RecipeResponse {
    return RecipeResponse.fromRecipe(saveRecipeUseCase.execute(recipeModifyRequest.toRecipe()))
  }

  @GetMapping("/favorites")
  fun favorites(): List<RecipeResponse> {
    return getFavoriteRecipesUseCase.execute().map { RecipeResponse.fromRecipe(it) }
  }

  @GetMapping("/latest")
  fun latest(): List<RecipeResponse> {
    return lastAddedRecipesUseCase.execute().map { RecipeResponse.fromRecipe(it) }
  }

  @PostMapping("/{id}/steps/{stepId}")
  fun addStepNote(@PathVariable stepId: Int, @RequestBody editStepNoteRequest: EditStepNoteRequest): StepResponse{
    return StepResponse.fromStep(editStepNoteUseCase.execute(stepId, editStepNoteRequest.note))
  }

  @PutMapping("/{id}/portions")
  fun updatePortions(@PathVariable id: Int, @RequestBody updatePortionsRequest: UpdatePortionsRequest): RecipeResponse {
    val recipe = updatePortionsUseCase.execute(id, updatePortionsRequest.portions)
    return RecipeResponse.fromRecipe(recipe)
  }
}
