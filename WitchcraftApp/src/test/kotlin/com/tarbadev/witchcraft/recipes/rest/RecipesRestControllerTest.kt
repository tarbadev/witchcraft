package com.tarbadev.witchcraft.recipes.rest

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
import org.apache.http.impl.client.HttpClientBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Arrays.asList


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipesRestControllerTest(
    @Autowired private var testRestTemplate: TestRestTemplate
) {
  private val testResources: TestResources = TestResources()
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
  @MockBean
  private lateinit var getRecipeNotesUseCase: GetRecipeNotesUseCase
  @MockBean
  private lateinit var editRecipeNotesUseCase: EditRecipeNotesUseCase

  @BeforeEach
  fun setup() {
    testRestTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build())
  }

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

    val responseEntity = testRestTemplate.getForEntity("/api/recipes", TestRecipeListResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualToComparingFieldByFieldRecursively(RecipeListResponse.fromRecipeList(recipes))
  }

  @Test
  fun show() {
    val recipe = Recipe(
        id = 33,
        name = "Test"
    )

    whenever(getRecipeUseCase.execute(recipe.id)).thenReturn(recipe)

    val responseEntity = testRestTemplate.getForEntity("/api/recipes/${recipe.id}", RecipeResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualTo(RecipeResponse.fromRecipe(recipe))
  }

  @Test
  fun setFavorite() {
    val id = 32
    val favorite = true
    val recipe = Recipe(id = id, favorite = favorite)

    whenever(setFavoriteRecipeUseCase.execute(id, favorite)).thenReturn(recipe)

    val returnedRecipe = testRestTemplate.patchForObject(
        "/api/recipes/$id",
        SetFavoriteRequest(favorite),
        RecipeResponse::class.java
    )

    assertThat(returnedRecipe).isEqualTo(RecipeResponse.fromRecipe(recipe))
  }

  @Test
  fun delete() {
    val id = 32

    whenever(doesRecipeExistUseCase.execute(id)).thenReturn(true)

    testRestTemplate.delete("/api/recipes/$id")

    verify(deleteRecipeUseCase).execute(id)
  }

  @Test
  fun delete_returnsNotFound_whenRecipeIsNotFound() {
    val id = 32

    whenever(doesRecipeExistUseCase.execute(id)).thenReturn(false)

    testRestTemplate.delete("/api/recipes/$id")

    verify(deleteRecipeUseCase, never()).execute(id)
  }

  @Test
  fun importFromUrl() {
    val recipe = testResources.recipe
    val recipeFormRequest = RecipeFormRequest(url = recipe.originUrl)

    whenever(getRecipeDetailsFromUrlUseCase.execute(recipe.originUrl)).thenReturn(recipe)
    whenever(saveRecipeUseCase.execute(recipe)).thenReturn(recipe)

    val responseEntity = testRestTemplate.postForEntity("/api/recipes/importFromUrl", recipeFormRequest, RecipeResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualTo(RecipeResponse.fromRecipe(recipe))

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

    val responseEntity = testRestTemplate.postForEntity("/api/recipes/importFromForm", recipeFormRequest, RecipeResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualTo(RecipeResponse.fromRecipe(recipe))
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

    testRestTemplate.put("/api/recipes/${recipeModifyRequest.id}/update", recipeModifyRequest)

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

    val responseEntity = testRestTemplate.getForEntity("/api/recipes/favorites", Array<RecipeResponse>::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body!!.toList()).isEqualTo(recipesResponse)
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

    val responseEntity = testRestTemplate.getForEntity("/api/recipes/latest", Array<RecipeResponse>::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body!!.toList()).isEqualTo(recipesResponse)
  }

  @Test
  fun getNotes() {
    val notes = Notes(recipeId = 15, comment = "Some notes")
    val notesResponse = NotesResponse.fromNotes(notes)

    whenever(getRecipeNotesUseCase.execute(15)).thenReturn(notes)

    val responseEntity = testRestTemplate.getForEntity("/api/recipes/15/notes", NotesResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualTo(notesResponse)
  }

  @Test
  fun editNotes() {
    val notesRequest = EditNotesRequest(recipeId = 15, notes = "Some notes")
    val notes = notesRequest.toNotes()
    val notesResponse = NotesResponse.fromNotes(notes)

    whenever(editRecipeNotesUseCase.execute(notes)).thenReturn(notes)

    val responseEntity = testRestTemplate.postForEntity("/api/recipes/15/notes", notesRequest, NotesResponse::class.java)

    assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON_UTF8)
    assertThat(responseEntity.body).isEqualTo(notesResponse)
  }

  data class TestRecipeListResponse(
      var recipes: List<RecipeResponse> = emptyList()
  )
}