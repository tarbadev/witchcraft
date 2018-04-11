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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecipesControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private TestResources testResources;
  @Autowired private AddRecipeUseCase addRecipeUseCase;
  @Autowired private RecipeCatalogUseCase recipeCatalogUseCase;
  @Autowired private GetRecipeDetailsUseCase getRecipeDetailsUseCase;
  @Autowired private GetRecipeUseCase getRecipeUseCase;

  @Before
  public void setUp() {
    Mockito.reset(addRecipeUseCase, recipeCatalogUseCase, getRecipeDetailsUseCase);
  }

  @Test
  public void newRecipe() throws Exception {
    RecipeManualForm recipeManualForm = new RecipeManualForm();

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
  public void import_SavesRecipe() throws Exception {
    Recipe recipe = testResources.getRecipe();

    given(getRecipeDetailsUseCase.execute(recipe.getUrl())).willReturn(recipe);
    given(addRecipeUseCase.execute(recipe)).willReturn(recipe);

    mvc.perform(post("/recipes/import")
        .param("url", recipe.getUrl())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    )
        .andExpect(redirectedUrl("/recipes"));

    verify(getRecipeDetailsUseCase).execute(recipe.getUrl());
    verify(addRecipeUseCase).execute(recipe);
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
}