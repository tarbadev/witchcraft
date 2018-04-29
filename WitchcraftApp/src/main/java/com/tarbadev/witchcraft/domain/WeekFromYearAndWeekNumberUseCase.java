package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

import java.util.Arrays;

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
              Day.builder().name(DayName.MONDAY).build(),
              Day.builder().name(DayName.TUESDAY).build(),
              Day.builder().name(DayName.WEDNESDAY).build(),
              Day.builder().name(DayName.THURSDAY).build(),
              Day.builder().name(DayName.FRIDAY).build(),
              Day.builder().name(DayName.SATURDAY).build(),
              Day.builder().name(DayName.SUNDAY).build()
          ))
          .build();
    }

    return week;
  }
}
