package com.tarbadev.witchcraft;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipesControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private RecipesService recipeService;

    @Test
    public void test_import_showsAddRecipeForm() throws Exception {
        mvc.perform(get("/recipes/import"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/index"))
                .andExpect(model().attribute("recipe", hasProperty("url", isEmptyOrNullString())));
    }

    @Test
    public void test_import_SavesRecipe() throws Exception {
        String url = "https://www.cookincanuck.com/mini-goat-cheese-stuffed-potato-appetizers/";
        Recipe recipe = new Recipe();
        recipe.setUrl(url);

        given(recipeService.addRecipe(recipe)).willReturn(true);

        mvc.perform(post("/recipes/import")
                .param("url", url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk());
    }
}