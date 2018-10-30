package com.tarbadev.witchcraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarbadev.witchcraft.TestResources;
import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import com.tarbadev.witchcraft.domain.usecase.*;
import com.tarbadev.witchcraft.web.RecipeFormRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipesRestControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private TestResources testResources;
  @Autowired
  private RecipeCatalogUseCase recipeCatalogUseCase;
  @Autowired
  private GetRecipeUseCase getRecipeUseCase;
  @Autowired
  private DeleteRecipeUseCase deleteRecipeUseCase;
  @Autowired
  private DoesRecipeExistUseCase doesRecipeExistUseCase;
  @Autowired
  private SetFavoriteRecipeUseCase setFavoriteRecipeUseCase;
  @Autowired
  private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  @Autowired
  private SaveRecipeUseCase saveRecipeUseCase;
  @Autowired
  private GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase;

  @Before
  public void setUp() {
    Mockito.reset(
        recipeCatalogUseCase,
        getRecipeUseCase,
        setFavoriteRecipeUseCase,
        deleteRecipeUseCase
    );
  }

  @Test
  public void list() throws Exception {
    String recipe1Name = "Test";
    String recipe1ImageUrl = "exampleImageUrl";
    Integer recipe1Id = 10;
    String recipe1Url = "/recipes/" + recipe1Id;
    String recipe2Name = "New Lasagna";

    Recipe recipe1 = Recipe.builder()
        .id(recipe1Id)
        .name(recipe1Name)
        .imgUrl(recipe1ImageUrl)
        .build();

    List<Recipe> recipes = Arrays.asList(
        recipe1,
        Recipe.builder().name(recipe2Name).build()
    );

    given(recipeCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/api/recipes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.recipes", hasSize(2)))
        .andExpect(jsonPath("$.recipes[0].id", is(recipe1Id)))
        .andExpect(jsonPath("$.recipes[0].name", is(recipe1Name)))
        .andExpect(jsonPath("$.recipes[0].imgUrl", is(recipe1ImageUrl)))
        .andExpect(jsonPath("$.recipes[0].url", is(recipe1Url)))
        .andExpect(jsonPath("$.recipes[1].name", is(recipe2Name)));
  }

  @Test
  public void show() throws Exception {
    Recipe recipe = Recipe.builder()
        .id(33)
        .name("Test")
        .url("url")
        .build();

    given(getRecipeUseCase.execute(recipe.getId())).willReturn(recipe);

    mvc.perform(get("/api/recipes/" + recipe.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(recipe.getId())))
        .andExpect(jsonPath("$.url", is(recipe.getUrl())))
        .andExpect(jsonPath("$.name", is(recipe.getName())));
  }

  @Test
  public void setFavorite() throws Exception {
    int id = 32;
    Boolean favorite = true;

    mvc.perform(patch(String.format("/api/recipes/%s", id))
        .contentType(MediaType.APPLICATION_JSON)
        .content(String.format("{ \"favorite\": %s }", favorite.toString()))
    )
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

    verify(setFavoriteRecipeUseCase).execute(id, favorite);
  }

  @Test
  public void delete() throws Exception {
    int id = 32;

    given(doesRecipeExistUseCase.execute(id)).willReturn(true);

    mvc.perform(MockMvcRequestBuilders.delete(String.format("/api/recipes/%s", id)))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

    verify(deleteRecipeUseCase).execute(id);
  }

  @Test
  public void delete_returnsNotFound_whenRecipeIsNotFound() throws Exception {
    int id = 32;

    given(doesRecipeExistUseCase.execute(id)).willReturn(false);

    mvc.perform(MockMvcRequestBuilders.delete(String.format("/api/recipes/%s", id)))
        .andExpect(status().isNotFound());

    verify(deleteRecipeUseCase, never()).execute(id);
  }

  @Test
  public void importFromUrl() throws Exception {
    Recipe recipe = testResources.getRecipe();
    RecipeFormRequest recipeFormRequest = RecipeFormRequest.builder().url(recipe.getOriginUrl()).build();

    given(getRecipeDetailsFromUrlUseCase.execute(recipe.getOriginUrl())).willReturn(recipe);
    given(saveRecipeUseCase.execute(recipe)).willReturn(recipe);

    mvc.perform(post("/api/recipes/importFromUrl")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(recipeFormRequest))
    )
        .andExpect(content().json(new ObjectMapper().writeValueAsString(recipe)));

    verify(getRecipeDetailsFromUrlUseCase).execute(recipe.getOriginUrl());
    verify(saveRecipeUseCase).execute(recipe);
  }

  @Test
  public void importFromForm() throws Exception {
    RecipeFormRequest recipeFormRequest = RecipeFormRequest.builder()
        .name("Some recipe name")
        .url("http://some/url/of/recipe")
        .imageUrl("http://some/url/of/recipe.png")
        .ingredients(String.join("\n"
            , "10 tbsp sugar"
            , "1/2 cup olive oil"
            , "1 lemon"
        ))
        .steps(String.join("\n"
            , "Add ingredients and stir"
            , "Serve on each plate"
        ))
        .build();
    Recipe recipe = Recipe.builder()
        .name(recipeFormRequest.getName())
        .originUrl(recipeFormRequest.getUrl())
        .imgUrl(recipeFormRequest.getImageUrl())
        .ingredients(Arrays.stream(recipeFormRequest.getIngredients().split("\n")).map(ingredient -> Ingredient.builder().name(ingredient).build()).collect(Collectors.toList()))
        .steps(Arrays.stream(recipeFormRequest.getSteps().split("\n")).map(step -> Step.builder().name(step).build()).collect(Collectors.toList()))
        .build();

    given(getRecipeDetailsFromFormUseCase.execute(
        recipeFormRequest.getName(),
        recipeFormRequest.getUrl(),
        recipeFormRequest.getIngredients(),
        recipeFormRequest.getSteps(),
        recipeFormRequest.getImageUrl()
    )).willReturn(recipe);
    given(saveRecipeUseCase.execute(recipe)).willReturn(recipe);

    mvc.perform(post("/api/recipes/importFromForm")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(recipeFormRequest))
    )
        .andExpect(content().json(new ObjectMapper().writeValueAsString(recipe)));
  }
}