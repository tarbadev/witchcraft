package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Day;
import com.tarbadev.witchcraft.domain.entity.DayName;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.repository.WeekRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

@Component
public class WeekFromYearAndWeekNumberUseCase {
  private final WeekRepository weekRepository;

  public WeekFromYearAndWeekNumberUseCase(WeekRepository weekRepository) {
    this.weekRepository = weekRepository;
  }

  public Week execute(Integer year, Integer weekNumber) {
    Week week = weekRepository.findByYearAndWeekNumber(year, weekNumber);

    if (week == null) {
      week = Week.builder()
          .year(year)
          .weekNumber(weekNumber)
          .days(Arrays.asList(
              Day.builder().name(DayName.MONDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.TUESDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.WEDNESDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.THURSDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.FRIDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.SATURDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build(),
              Day.builder().name(DayName.SUNDAY).lunch(Recipe.builder().build()).diner(Recipe.builder().build()).build()
          ))
          .build();
    }

    week.getDays().sort(Comparator.comparing(Day::getName));

    return week;
  }
}
