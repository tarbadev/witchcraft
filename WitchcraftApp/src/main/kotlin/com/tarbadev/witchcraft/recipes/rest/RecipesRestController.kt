package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.*
import com.tarbadev.witchcraft.recipes.rest.entity.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.units.indriya.ComparableQuantity

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
    private val lastAddedRecipesUseCase: LastAddedRecipesUseCase
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
                    quantity = getQuantity(ingredientModifyForm.unit, ingredientModifyForm.quantity)
                )
            },
            steps = recipeModifyRequest.steps.map { stepModifyForm ->
                Step(
                    id = stepModifyForm.id,
                    name = stepModifyForm.name
                )
            }
        )

        return RecipeResponse.fromRecipe(saveRecipeUseCase.execute(recipe))
    }

    @GetMapping("/favorites")
    fun favorites(): List<RecipeResponse> {
        return getFavoriteRecipesUseCase.execute().map { RecipeResponse.fromRecipe(it) }
    }

    @GetMapping("/latest")
    fun latest(): List<RecipeResponse> {
        return lastAddedRecipesUseCase.execute().map { RecipeResponse.fromRecipe(it) }
    }
}
