package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.GetCurrentWeekUseCase;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.RecipeCatalogUseCase;
import com.tarbadev.witchcraft.domain.Week;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WeekControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private GetCurrentWeekUseCase getCurrentWeekUseCase;
  @Autowired private RecipeCatalogUseCase recipesCatalogUseCase;

  @Before
  public void setUp() {
    Mockito.reset(getCurrentWeekUseCase, recipesCatalogUseCase);
  }

  @Test
  public void index() throws Exception {
    Week week = Week.builder().build();
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );

    given(getCurrentWeekUseCase.execute()).willReturn(week);
    given(recipesCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/weeks"))
        .andExpect(status().isOk())
        .andExpect(view().name("weeks/index"))
        .andExpect(model().attribute("week", week))
        .andExpect(model().attribute("recipes", recipes));
  }
}