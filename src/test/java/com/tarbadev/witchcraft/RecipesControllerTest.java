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

    @Before
    public void setUp() {
        Mockito.reset(addRecipeUseCase);
    }

    @Test
    public void test_import_showsAddRecipeForm() throws Exception {
        mvc.perform(get("/recipes/import"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/index"))
                .andExpect(model().attribute("recipeForm", hasProperty("url", isEmptyOrNullString())));
    }

    @Test
    public void test_import_SavesRecipe() throws Exception {
        String url = testResources.getRecipeUrl();
        Recipe recipe = Recipe.builder().url(url).build();

        given(addRecipeUseCase.execute(url)).willReturn(recipe);

        mvc.perform(post("/recipes/import")
                .param("url", url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk());

        verify(addRecipeUseCase).execute(url);
    }
}