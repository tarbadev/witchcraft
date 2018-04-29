package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.*;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WeekControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private RecipeCatalogUseCase recipesCatalogUseCase;
  @Autowired private SaveWeekUseCase saveWeekUseCase;
  @Autowired private WeekNavForWeekUseCase weekNavForWeekUseCase;
  @Autowired private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;

  @Before
  public void setUp() {
    Mockito.reset(
        recipesCatalogUseCase,
        saveWeekUseCase,
        weekNavForWeekUseCase,
        weekFromYearAndWeekNumberUseCase
    );
  }

  @Test
  public void index() throws Exception {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

    mvc.perform(get("/weeks"))
        .andExpect(redirectedUrl(String.format("/weeks/%s/%s", year, weekNumber)));
  }

  @Test
  public void save() throws Exception {
    WeekForm weekForm = WeekForm.builder()
        .id(345)
        .year(2018)
        .days(Arrays.asList(
            DayForm.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunchId(57)
                .build(),
            DayForm.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .dinerId(90)
                .build()
        ))
        .build();

    mvc.perform(patch("/weeks/12/save")
        .flashAttr("weekForm", weekForm))
        .andExpect(redirectedUrl("/weeks"));

    Week week = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(Recipe.builder().id(57).build())
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(Recipe.builder().id(90).build())
                .build()
        ))
        .build();

    verify(saveWeekUseCase).execute(week);
  }

  @Test
  public void save_post() throws Exception {
    WeekForm weekForm = WeekForm.builder()
        .id(345)
        .year(2018)
        .days(Arrays.asList(
            DayForm.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunchId(57)
                .build(),
            DayForm.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .dinerId(90)
                .build()
        ))
        .build();

    mvc.perform(post("/weeks/12/save")
        .param("_method", "patch")
        .flashAttr("weekForm", weekForm))
        .andExpect(redirectedUrl("/weeks"));

    Week week = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(Recipe.builder().id(57).build())
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(Recipe.builder().id(90).build())
                .build()
        ))
        .build();

    verify(saveWeekUseCase).execute(week);
  }

  @Test
  public void show() throws Exception {
    Week week = Week.builder().year(2018).weekNumber(12).days(Collections.emptyList()).build();

    given(weekFromYearAndWeekNumberUseCase.execute(2018, 12)).willReturn(week);
    given(weekNavForWeekUseCase.execute(week)).willReturn(WeekNav.builder().build());

    mvc.perform(get("/weeks/2018/12"))
        .andExpect(status().isOk())
        .andExpect(view().name("weeks/index"))
        .andExpect(model().attribute("week", week));
  }

  @Test
  public void show_returnsWeekFormWithWeekInformation() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );
    Week week = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(36)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(Recipe.builder().id(57).build())
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(Recipe.builder().id(90).build())
                .build()
        ))
        .build();

    WeekForm weekForm = WeekForm.builder()
        .id(345)
        .year(2018)
        .days(Arrays.asList(
            DayForm.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunchId(57)
                .build(),
            DayForm.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .dinerId(90)
                .build()
        ))
        .build();

    WeekNav weekNav = WeekNav.builder()
        .prevWeekNumber(35)
        .prevYear(2018)
        .nextWeekNumber(37)
        .nextYear(2018)
        .build();

    given(weekFromYearAndWeekNumberUseCase.execute(2018, 36)).willReturn(week);
    given(weekNavForWeekUseCase.execute(week)).willReturn(weekNav);
    given(recipesCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/weeks/2018/36"))
        .andExpect(status().isOk())
        .andExpect(view().name("weeks/index"))
        .andExpect(model().attribute("week", week))
        .andExpect(model().attribute("weekNav", weekNav))
        .andExpect(model().attribute("weekForm", weekForm))
        .andExpect(model().attribute("recipes", recipes));
  }
}