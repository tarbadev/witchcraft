package com.tarbadev.witchcraft.recipes.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.*
import com.tarbadev.witchcraft.recipes.rest.entity.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.Arrays.asList


@ExtendWith(SpringExtension::class)
@WebMvcTest(RecipesRestController::class)
class RecipesRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
  @MockBean
  private lateinit var recipeCatalogUseCase: RecipeCatalogUseCase
  @MockBean
  private lateinit var getRecipeUseCase: GetRecipeUseCase
  @MockBean
  private lateinit var deleteRecipeUseCase: DeleteRecipeUseCase
  @MockBean
  private lateinit var doesRecipeExistUseCase: DoesRecipeExistUseCase
  @MockBean
  private lateinit var setFavoriteRecipeUseCase: SetFavoriteRecipeUseCase
  @MockBean
  private lateinit var getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase
  @MockBean
  private lateinit var saveRecipeUseCase: SaveRecipeUseCase
  @MockBean
  private lateinit var getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase
  @MockBean
  private lateinit var getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
  @MockBean
  private lateinit var lastAddedRecipesUseCase: LastAddedRecipesUseCase

  @Test
  fun list() {
    val recipe1Name = "Test"
    val recipe1ImageUrl = "exampleImageUrl"
    val recipe1Id = 10
    val recipe2Name = "New Lasagna"

    val recipe1 = Recipe(
        id = recipe1Id,
        name = recipe1Name,
        imgUrl = recipe1ImageUrl
    )

    val recipes = asList(
        recipe1,
        Recipe(name = recipe2Name)
    )

    whenever(recipeCatalogUseCase.execute()).thenReturn(recipes)

    mockMvc.perform(get("/api/recipes"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeListResponse.fromRecipeList(recipes))))
  }

  @Test
  fun show() {
    val recipe = Recipe(
        id = 33,
        name = "Test"
    )

    whenever(getRecipeUseCase.execute(recipe.id)).thenReturn(recipe)

    mockMvc.perform(get("/api/recipes/${recipe.id}"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeResponse.fromRecipe(recipe))))
  }

  @Test
  fun setFavorite() {
    val id = 32
    val favorite = true
    val recipe = Recipe(id = id, favorite = favorite)

    whenever(setFavoriteRecipeUseCase.execute(id, favorite)).thenReturn(recipe)

    mockMvc.perform(patch("/api/recipes/${recipe.id}")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(SetFavoriteRequest(favorite)))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeResponse.fromRecipe(recipe))))
  }

  @Test
  fun delete() {
    val id = 32

    whenever(doesRecipeExistUseCase.execute(id)).thenReturn(true)

    mockMvc.perform(delete("/api/recipes/$id"))
        .andExpect(status().isNoContent)

    verify(deleteRecipeUseCase).execute(id)
  }

  @Test
  fun delete_returnsNotFound_whenRecipeIsNotFound() {
    val id = 32

    whenever(doesRecipeExistUseCase.execute(id)).thenReturn(false)

    mockMvc.perform(delete("/api/recipes/$id"))
        .andExpect(status().isNotFound)

    verify(deleteRecipeUseCase, never()).execute(id)
  }

  @Test
  fun update() {
    val recipeModifyRequest = RecipeModifyRequest(
        id = 12,
        name = "Recipe Name",
        url = "http://fake/url",
        imgUrl = "http://fake/url.png",
        favorite = true,
        ingredients = listOf(IngredientModifyRequest(name = "Ingredient")),
        steps = listOf(StepModifyRequest(name = "First Step"))
    )

    val recipe = Recipe(
        id = recipeModifyRequest.id,
        name = recipeModifyRequest.name,
        originUrl = recipeModifyRequest.url,
        imgUrl = recipeModifyRequest.imgUrl,
        favorite = recipeModifyRequest.favorite,
        ingredients = recipeModifyRequest.ingredients
            .map { ingredientModifyRequest ->
              Ingredient(
                  id = ingredientModifyRequest.id,
                  name = ingredientModifyRequest.name,
                  unit = ingredientModifyRequest.unit,
                  quantity = ingredientModifyRequest.quantity
              )
            },
        steps = recipeModifyRequest.steps
            .map { stepModifyRequest ->
              Step(
                  id = stepModifyRequest.id,
                  name = stepModifyRequest.name
              )
            },
        isArchived = false
    )

    whenever(saveRecipeUseCase.execute(recipe)).thenReturn(recipe)

    mockMvc.perform(put("/api/recipes/${recipeModifyRequest.id}/update")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(recipeModifyRequest))
    )
        .andExpect(status().isOk)

    verify(saveRecipeUseCase).execute(recipe)
  }

  @Test
  fun favorites() {
    val recipe1 = Recipe()
    val recipe2 = Recipe()
    val recipe3 = Recipe()

    val recipes = asList(recipe1, recipe2, recipe3)

    val recipesResponse = asList(
        RecipeResponse.fromRecipe(recipe1),
        RecipeResponse.fromRecipe(recipe2),
        RecipeResponse.fromRecipe(recipe3)
    )

    whenever(getFavoriteRecipesUseCase.execute()).thenReturn(recipes)

    mockMvc.perform(get("/api/recipes/favorites"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(recipesResponse)))
  }

  @Test
  fun latest() {
    val recipe1 = Recipe()
    val recipe2 = Recipe()
    val recipe3 = Recipe()

    val recipes = asList(recipe1, recipe2, recipe3)

    val recipesResponse = asList(
        RecipeResponse.fromRecipe(recipe1),
        RecipeResponse.fromRecipe(recipe2),
        RecipeResponse.fromRecipe(recipe3)
    )

    whenever(lastAddedRecipesUseCase.execute()).thenReturn(recipes)

    mockMvc.perform(get("/api/recipes/latest"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(recipesResponse)))
  }
}