package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class WeekNavForWeekUseCase {
  public WeekNav execute(Week week) {
    Integer prevWeekNumber = week.getWeekNumber() - 1;
    Integer prevYear = week.getYear();
    Integer nextWeekNumber = week.getWeekNumber() + 1;
    Integer nextYear = week.getYear();

    if (nextWeekNumber > 52) {
      nextWeekNumber = 1;
      nextYear += 1;
    }

    if (prevWeekNumber < 1) {
      prevWeekNumber = 52;
      prevYear -= 1;
    }

    return WeekNav.builder()
        .prevWeekNumber(prevWeekNumber)
        .prevYear(prevYear)
        .nextWeekNumber(nextWeekNumber)
        .nextYear(nextYear)
        .build();
  }
}
