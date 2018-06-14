package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.RecipeCatalogUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipesRestControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private RecipeCatalogUseCase recipeCatalogUseCase;

  @Before
  public void setUp() {
    Mockito.reset(recipeCatalogUseCase);
  }

  @Test
  public void list() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().name("Test").build(),
        Recipe.builder().name("New Lasagna").build()
    );

    given(recipeCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/api/recipes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.recipes", hasSize(2)))
        .andExpect(jsonPath("$.recipes[0].name", is("Test")))
        .andExpect(jsonPath("$.recipes[1].name", is("New Lasagna")));
  }
}