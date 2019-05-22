package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.*
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeFormRequest
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeListResponse
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeModifyRequest
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
    private val getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val lastAddedRecipesUseCase: LastAddedRecipesUseCase
) {
    @GetMapping
    fun list(): RecipeListResponse {
        val recipes = recipeCatalogUseCase.execute()

        return RecipeListResponse(recipes)
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Int): Recipe? {
        return getRecipeUseCase.execute(id)
    }

    @PatchMapping("/{id}")
    fun setFavorite(
        @PathVariable("id") id: Int,
        @RequestBody requestParams: Map<String, String>
    ): Recipe {
        val favorite = java.lang.Boolean.parseBoolean(requestParams["favorite"])
        return setFavoriteRecipeUseCase.execute(id, favorite)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Int): ResponseEntity<*> {
        if (!doesRecipeExistUseCase.execute(id)) {
            return ResponseEntity.notFound().build<Any>()
        }

        deleteRecipeUseCase.execute(id)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Any>()
    }

    @PostMapping("/importFromUrl")
    fun importFromUrl(@RequestBody recipeFormRequest: RecipeFormRequest): Recipe {
        val recipe = getRecipeDetailsFromUrlUseCase.execute(recipeFormRequest.url)

        return saveRecipeUseCase.execute(recipe)
    }

    @PostMapping("/importFromForm")
    fun importFromForm(@RequestBody recipeFormRequest: RecipeFormRequest): Recipe {
        val recipe = getRecipeDetailsFromFormUseCase.execute(
            recipeFormRequest.name,
            recipeFormRequest.url,
            recipeFormRequest.ingredients,
            recipeFormRequest.steps,
            recipeFormRequest.imageUrl
        )

        return saveRecipeUseCase.execute(recipe)
    }

    @PutMapping("/{id}/update")
    fun update(@RequestBody recipeModifyRequest: RecipeModifyRequest): Recipe {
        val recipe = Recipe(
            id = recipeModifyRequest.id,
            name = recipeModifyRequest.name,
            originUrl = recipeModifyRequest.url,
            imgUrl = recipeModifyRequest.imgUrl,
            favorite = recipeModifyRequest.favorite,
            ingredients = recipeModifyRequest.ingredients.map { ingredientModifyForm ->
                Ingredient(
                    id = ingredientModifyForm.id,
                    name = ingredientModifyForm.name,
                    unit = ingredientModifyForm.unit,
                    quantity = ingredientModifyForm.quantity
                )
            },
            steps = recipeModifyRequest.steps.map { stepModifyForm ->
                Step(
                    id = stepModifyForm.id,
                    name = stepModifyForm.name
                )
            }
        )

        return saveRecipeUseCase.execute(recipe)
    }

    @GetMapping("/favorites")
    fun favorites(): List<Recipe> {
        return getFavoriteRecipesUseCase.execute()
    }

    @GetMapping("/latest")
    fun latest(): List<Recipe> {
        return lastAddedRecipesUseCase.execute()
    }
}
