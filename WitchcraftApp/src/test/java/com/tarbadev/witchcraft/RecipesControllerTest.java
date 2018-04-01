package com.tarbadev.witchcraft;

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
    public void test_index_showsAddRecipeForm() throws Exception {
        mvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/index"))
                .andExpect(model().attribute("recipeForm", hasProperty("url", isEmptyOrNullString())));
    }

    @Test
    public void test_index_ShowsAllRecipes() throws Exception {
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
    public void test_import_SavesRecipe() throws Exception {
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
    public void test_show_ShowRecipesDetails() throws Exception {
        Recipe recipe = testResources.getRecipe();

        Integer recipeId = 123;
        given(getRecipeUseCase.execute(recipeId)).willReturn(recipe);

        mvc.perform(get(String.format("/recipes/%d", recipeId)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attribute("recipe", recipe));
    }
}