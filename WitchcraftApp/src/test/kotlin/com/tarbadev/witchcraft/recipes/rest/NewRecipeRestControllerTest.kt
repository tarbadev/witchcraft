package com.tarbadev.witchcraft.recipes.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.*
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
@WebMvcTest(NewRecipeRestController::class)
class NewRecipeRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
  @MockBean
  private lateinit var getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase
  @MockBean
  private lateinit var saveRecipeUseCase: SaveRecipeUseCase
  @MockBean
  private lateinit var getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase
  @MockBean
  private lateinit var getSupportedDomainsUseCase: GetSupportedDomainsUseCase
  @MockBean
  private lateinit var addExpressRecipeUseCase: AddExpressRecipeUseCase

  @Test
  fun importFromUrl() {
    val recipe = TestResources.Recipes.cookinCanuck
    val recipeFormRequest = RecipeFormRequest(url = recipe.originUrl)

    whenever(getRecipeDetailsFromUrlUseCase.execute(recipe.originUrl)).thenReturn(recipe)
    whenever(saveRecipeUseCase.execute(recipe)).thenReturn(recipe)

    mockMvc.perform(post("/api/recipes/import-from-url")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(recipeFormRequest))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeResponse.fromRecipe(recipe))))

    verify(getRecipeDetailsFromUrlUseCase).execute(recipe.originUrl)
    verify(saveRecipeUseCase).execute(recipe)
  }

  @Test
  fun importFromForm() {
    val recipeFormRequest = RecipeFormRequest(
        name = "Some recipe name",
        url = "http://some/url/of/recipe",
        imageUrl = "http://some/url/of/recipe.png",
        ingredients = arrayOf("10 tbsp sugar", "1/2 cup olive oil", "1 lemon").joinToString("\n"),
        steps = arrayOf("Add ingredients and stir", "Serve on each plate").joinToString("\n")
    )
    val recipe = Recipe(
        name = recipeFormRequest.name,
        originUrl = recipeFormRequest.url,
        imgUrl = recipeFormRequest.imageUrl,
        ingredients = recipeFormRequest.ingredients
            .split("\n")
            .dropLastWhile { it.isEmpty() }
            .map { ingredient -> Ingredient(name = ingredient) },
        steps = recipeFormRequest.steps
            .split("\n")
            .dropLastWhile { it.isEmpty() }
            .map { step -> Step(name = step) }
    )

    whenever(getRecipeDetailsFromFormUseCase.execute(
        recipeFormRequest.name,
        recipeFormRequest.url,
        recipeFormRequest.ingredients,
        recipeFormRequest.steps,
        recipeFormRequest.imageUrl
    )).thenReturn(recipe)
    whenever(saveRecipeUseCase.execute(recipe)).thenReturn(recipe)

    mockMvc.perform(post("/api/recipes/import-from-form")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(recipeFormRequest))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeResponse.fromRecipe(recipe))))
  }

  @Test
  fun getSupportedDomains() {
    val supportedDomains = listOf(
        SupportedDomain(
            "Some Vendor",
            "www.some.vendor.example.com/recipes",
            "www.some.vendor.example.com/logo.png"
        )
    )
    val supportedDomainsResponse = supportedDomains.map { SupportedDomainResponse.fromSupportedDomain(it) }

    whenever(getSupportedDomainsUseCase.execute()).thenReturn(supportedDomains)

    mockMvc.perform(get("/api/recipes/import-from-url/supported"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(supportedDomainsResponse)))
  }

  @Test
  fun addExpressRecipe() {
    val expressRecipeRequest = ExpressRecipeRequest("Lasagna")
    val recipe = Recipe(id = 3, name = "Lasagna")

    whenever(addExpressRecipeUseCase.execute(expressRecipeRequest.toRecipe())).thenReturn(recipe)

    mockMvc.perform(post("/api/recipes/express")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(expressRecipeRequest))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(RecipeResponse.fromRecipe(recipe))))
  }
}