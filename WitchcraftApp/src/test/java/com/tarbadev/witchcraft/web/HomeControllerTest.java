package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.usecase.GetFavoriteRecipesUseCase;
import com.tarbadev.witchcraft.domain.usecase.LastAddedRecipesUseCase;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
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
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HomeControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;
  @Autowired private GetFavoriteRecipesUseCase getFavoriteRecipesUseCase;
  @Autowired private LastAddedRecipesUseCase lastAddedRecipesUseCase;

  @Before
  public void setUp() {
    Mockito.reset(
        weekFromYearAndWeekNumberUseCase,
        getFavoriteRecipesUseCase,
        lastAddedRecipesUseCase
    );
  }

  @Test
  public void index_showsCurrentWeek() throws Exception {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    Week week = Week.builder().build();

    given(weekFromYearAndWeekNumberUseCase.execute(year, weekNumber)).willReturn(week);

    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attribute("currentWeek", week));
  }

  @Test
  public void index_showsBestRatedRecipes() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );

    given(getFavoriteRecipesUseCase.execute()).willReturn(recipes);
    given(weekFromYearAndWeekNumberUseCase.execute(any(), any())).willReturn(Week.builder().build());

    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attribute("bestRecipes", recipes));
  }

  @Test
  public void index_showsLastAddedRecipes() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );

    given(lastAddedRecipesUseCase.execute()).willReturn(recipes);
    given(weekFromYearAndWeekNumberUseCase.execute(any(), any())).willReturn(Week.builder().build());

    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attribute("lastAddedRecipes", recipes));
  }
}