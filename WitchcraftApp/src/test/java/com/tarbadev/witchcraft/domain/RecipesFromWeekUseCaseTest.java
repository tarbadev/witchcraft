package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipesFromWeekUseCaseTest {
  private RecipesFromWeekUseCase subject;

  @Before
  public void setUp() {
    subject = new RecipesFromWeekUseCase();
  }

  @Test
  public void execute() {
    Recipe lunch = Recipe.builder().id(57).build();
    Recipe diner = Recipe.builder().id(90).build();
    List<Recipe> recipes = Arrays.asList(lunch, diner);
    Week week = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(36)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(lunch)
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(diner)
                .build()
        ))
        .build();

    assertThat(subject.execute(week)).isEqualTo(recipes);
  }
}