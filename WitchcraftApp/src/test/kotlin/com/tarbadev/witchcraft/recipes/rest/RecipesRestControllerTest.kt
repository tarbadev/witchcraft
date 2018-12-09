package com.tarbadev.witchcraft.recipes.rest

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.*
import org.apache.http.impl.client.HttpClientBuilder
import org.junit.jupiter.api.Assertions
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
    @MockBean private lateinit var recipeCatalogUseCase: RecipeCatalogUseCase
    @MockBean private lateinit var getRecipeUseCase: GetRecipeUseCase
    @MockBean private lateinit var deleteRecipeUseCase: DeleteRecipeUseCase
    @MockBean private lateinit var doesRecipeExistUseCase: DoesRecipeExistUseCase
    @MockBean private lateinit var setFavoriteRecipeUseCase: SetFavoriteRecipeUseCase
    @MockBean private lateinit var getRecipeDetailsFromUrlUseCase: GetRecipeDetailsFromUrlUseCase
    @MockBean private lateinit var saveRecipeUseCase: SaveRecipeUseCase
    @MockBean private lateinit var getRecipeDetailsFromFormUseCase: GetRecipeDetailsFromFormUseCase
    @MockBean private lateinit var getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
    @MockBean private lateinit var lastAddedRecipesUseCase: LastAddedRecipesUseCase

    @BeforeEach
    fun setup() {
        testRestTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build())
    }

    @Test
    fun list() {
        val recipe1Name = "Test"
        val recipe1ImageUrl = "exampleImageUrl"
        val recipe1Id = 10
        val recipe1Url = "/recipes/$recipe1Id"
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

        val responseEntity = testRestTemplate.getForEntity("/api/recipes", RecipeList::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(RecipeList(recipes), responseEntity.body)
    }

    @Test
    fun show() {
        val recipe = Recipe(
            id = 33,
            name = "Test"
        )

        whenever(getRecipeUseCase.execute(recipe.id)).thenReturn(recipe)

        val responseEntity = testRestTemplate.getForEntity("/api/recipes/${recipe.id}", Recipe::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(recipe, responseEntity.body)
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
            Recipe::class.java
        )

        Assertions.assertEquals(recipe, returnedRecipe)
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

        val responseEntity = testRestTemplate.postForEntity("/api/recipes/importFromUrl", recipeFormRequest, Recipe::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(recipe, responseEntity.body)

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

        val responseEntity = testRestTemplate.postForEntity("/api/recipes/importFromForm", recipeFormRequest, Recipe::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(recipe, responseEntity.body)
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
                }
        )

        whenever(saveRecipeUseCase.execute(recipe)).thenReturn(recipe)

        testRestTemplate.put("/api/recipes/${recipeModifyRequest.id}/update", recipeModifyRequest)

        verify(saveRecipeUseCase).execute(recipe)
    }

    @Test
    fun favorites() {
        val recipes = asList(
            Recipe(),
            Recipe(),
            Recipe()
        )

        whenever(getFavoriteRecipesUseCase.execute()).thenReturn(recipes)

        val responseEntity = testRestTemplate.getForEntity("/api/recipes/favorites", Array<Recipe>::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(recipes, responseEntity.body!!.toList())
    }

    @Test
    fun latest() {
        val recipes = asList(
            Recipe(),
            Recipe(),
            Recipe()
        )

        whenever(lastAddedRecipesUseCase.execute()).thenReturn(recipes)

        val responseEntity = testRestTemplate.getForEntity("/api/recipes/latest", Array<Recipe>::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(recipes, responseEntity.body!!.toList())
    }
}