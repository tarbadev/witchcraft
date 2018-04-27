package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SaveWeekUseCase {
  private final WeekRepository weekRepository;
  private final RecipeRepository recipeRepository;

  public SaveWeekUseCase(WeekRepository weekRepository, RecipeRepository recipeRepository) {
    this.weekRepository = weekRepository;
    this.recipeRepository = recipeRepository;
  }

  public void execute(Week week) {
    Week weekWithRecipes = Week.builder()
        .id(week.getId())
        .year(week.getYear())
        .weekNumber(week.getWeekNumber())
        .days(week.getDays().stream()
            .map(day -> Day.builder()
                .id(day.getId())
                .name(day.getName())
                .lunch(day.getLunch() != null ? recipeRepository.findById(day.getLunch().getId()) : null)
                .diner(day.getDiner() != null ? recipeRepository.findById(day.getDiner().getId()) : null)
                .build())
            .collect(Collectors.toList()))
        .build();

    weekRepository.save(weekWithRecipes);
  }
}
