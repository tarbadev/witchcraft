package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.*;
import com.tarbadev.witchcraft.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecipesControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private TestResources testResources;
  @Autowired private SaveRecipeUseCase saveRecipeUseCase;
  @Autowired private RecipeCatalogUseCase recipeCatalogUseCase;
  @Autowired private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  @Autowired private GetRecipeUseCase getRecipeUseCase;
  @Autowired private GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase;
  @Autowired private DeleteRecipeUseCase deleteRecipeUseCase;
  @Autowired private RateRecipeUseCase rateRecipeUseCase;

  @Before
  public void setUp() {
    Mockito.reset(
        saveRecipeUseCase,
        recipeCatalogUseCase,
        getRecipeDetailsFromUrlUseCase,
        getRecipeDetailsFromFormUseCase,
        deleteRecipeUseCase,
        rateRecipeUseCase
    );
  }

  @Test
  public void newRecipe() throws Exception {
    mvc.perform(get("/recipes/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipes/newRecipe"))
        .andExpect(model().attribute("recipeUrlForm", hasProperty("url", isEmptyOrNullString())))
        .andExpect(model().attribute("recipeManualForm", hasProperty("name", isEmptyOrNullString())))
        .andExpect(model().attribute("recipeManualForm", hasProperty("url", isEmptyOrNullString())))
        .andExpect(model().attribute("recipeManualForm", hasProperty("ingredients", isEmptyOrNullString())))
        .andExpect(model().attribute("recipeManualForm", hasProperty("steps", isEmptyOrNullString())));
  }

  @Test
  public void index() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build()
    );

    given(recipeCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/recipes"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipes/index"))
        .andExpect(model().attribute("recipes", recipes));
  }

  @Test
  public void importFromUrl() throws Exception {
    Recipe recipe = testResources.getRecipe();

    given(getRecipeDetailsFromUrlUseCase.execute(recipe.getOriginUrl())).willReturn(recipe);

    mvc.perform(post("/recipes/importFromUrl")
        .param("url", recipe.getOriginUrl())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    )
        .andExpect(redirectedUrl("/recipes"));

    verify(getRecipeDetailsFromUrlUseCase).execute(recipe.getOriginUrl());
    verify(saveRecipeUseCase).execute(recipe);
  }

  @Test
  public void importFromForm() throws Exception {
    String recipeName = "Some recipe name";
    String recipeOriginUrl = "http://some/url/of/recipe";
    String recipeImgUrl = "http://some/url/of/recipe.png";
    String recipeIngredients = String.join("\n"
        , "10 tbsp sugar"
        , "1/2 cup olive oil"
        , "1 lemon"
    );
    String recipeSteps = String.join("\n"
        , "Add ingredients and stir"
        , "Serve on each plate"
    );

    RecipeManualForm recipeManualForm = new RecipeManualForm();
    recipeManualForm.setName(recipeName);
    recipeManualForm.setUrl(recipeOriginUrl);
    recipeManualForm.setImgUrl(recipeImgUrl);
    recipeManualForm.setIngredients(recipeIngredients);
    recipeManualForm.setSteps(recipeSteps);

    Recipe recipe = Recipe.builder()
        .name(recipeName)
        .originUrl(recipeOriginUrl)
        .imgUrl(recipeImgUrl)
        .ingredients(Arrays.stream(recipeIngredients.split("\n")).map(ingredient -> Ingredient.builder().name(ingredient).build()).collect(Collectors.toList()))
        .steps(Arrays.stream(recipeSteps.split("\n")).map(step -> Step.builder().name(step).build()).collect(Collectors.toList()))
        .build();

    given(getRecipeDetailsFromFormUseCase.execute(recipeName, recipeOriginUrl, recipeIngredients, recipeSteps, recipeImgUrl)).willReturn(recipe);

    mvc.perform(post("/recipes/importFromForm")
        .param("name", recipeManualForm.getName())
        .param("url", recipeManualForm.getUrl())
        .param("imgUrl", recipeManualForm.getImgUrl())
        .param("ingredients", recipeManualForm.getIngredients())
        .param("steps", recipeManualForm.getSteps())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    )
        .andExpect(redirectedUrl("/recipes"));

    verify(getRecipeDetailsFromFormUseCase).execute(recipeName, recipeOriginUrl, recipeIngredients, recipeSteps, recipeImgUrl);
    verify(saveRecipeUseCase).execute(recipe);
  }

  @Test
  public void show() throws Exception {
    Recipe recipe = testResources.getRecipe();

    Integer recipeId = 123;
    given(getRecipeUseCase.execute(recipeId)).willReturn(recipe);

    mvc.perform(get(String.format("/recipes/%d", recipeId)))
        .andExpect(status().isOk())
        .andExpect(view().name("recipes/show"))
        .andExpect(model().attribute("recipe", recipe));
  }

  @Test
  public void deleteRecipe() throws Exception {
    mvc.perform(delete("/recipes/123"))
        .andExpect(redirectedUrl("/recipes"));

    verify(deleteRecipeUseCase).execute(123);
  }

  @Test
  public void deleteRecipe_usingPost() throws Exception {
    mvc.perform(post("/recipes/123").param("_method", "delete"))
        .andExpect(redirectedUrl("/recipes"));

    verify(deleteRecipeUseCase).execute(123);
  }

  @Test
  public void rate() throws Exception {
    mvc.perform(patch("/recipes/123/rate/4.5"))
        .andExpect(redirectedUrl("/recipes/123"));

    verify(rateRecipeUseCase).execute(123, 4.5);
  }

  @Test
  public void rate_usingPost() throws Exception {
    mvc.perform(post("/recipes/123/rate/4.5").param("_method", "patch"))
        .andExpect(redirectedUrl("/recipes/123"));

    verify(rateRecipeUseCase).execute(123, 4.5);
  }

  @Test
  public void showModify() throws Exception {
    Recipe recipe = Recipe.builder().id(123).build();

    given(getRecipeUseCase.execute(recipe.getId())).willReturn(recipe);

    mvc.perform(get(String.format("/recipes/%s/modify", recipe.getId())))
        .andExpect(status().isOk())
        .andExpect(view().name("recipes/modify"))
        .andExpect(model().attribute("recipe", recipe));
  }

  @Test
  public void modify() throws Exception {
    RecipeModifyForm recipeModifyForm = RecipeModifyForm.builder()
        .id(123)
        .name("Recipe name")
        .url("http originUrl")
        .imgUrl("http img originUrl")
        .ingredients(Arrays.asList(
            IngredientModifyForm.builder().quantity(2.0).name("Ingredient").build(),
            IngredientModifyForm.builder().quantity(1.5).unit("oz").name("Ingredient 3").build(),
            IngredientModifyForm.builder().quantity(1.0).unit("tbsp").name("Ingredient 2").build()
        ))
        .steps(Arrays.asList(
            StepModifyForm.builder().name("Step one").build(),
            StepModifyForm.builder().name("Step two").build()
        ))
        .build();

    Recipe recipe = Recipe.builder()
        .id(123)
        .name("Recipe name")
        .originUrl("http originUrl")
        .imgUrl("http img originUrl")
        .ingredients(Arrays.asList(
            Ingredient.builder().quantity(2.0).name("Ingredient").build(),
            Ingredient.builder().quantity(1.5).unit("oz").name("Ingredient 3").build(),
            Ingredient.builder().quantity(1.0).unit("tbsp").name("Ingredient 2").build()
        ))
        .steps(Arrays.asList(
            Step.builder().name("Step one").build(),
            Step.builder().name("Step two").build()
        ))
        .build();

    mvc.perform(patch("/recipes/123/modify").flashAttr("recipeModifyForm", recipeModifyForm))
        .andExpect(redirectedUrl("/recipes/123"));

    verify(saveRecipeUseCase).execute(recipe);
  }
}