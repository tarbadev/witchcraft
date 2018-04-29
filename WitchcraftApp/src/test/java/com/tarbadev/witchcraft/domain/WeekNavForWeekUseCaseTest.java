package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeekNavForWeekUseCaseTest {
  private WeekNavForWeekUseCase subject;

  @Before
  public void setUp() {
    subject = new WeekNavForWeekUseCase();
  }

  @Test
  public void execute() {
    Week week = Week.builder()
        .year(2018)
        .weekNumber(36)
        .build();
    WeekNav weekNav = WeekNav.builder()
        .prevWeekNumber(week.getWeekNumber() - 1)
        .prevYear(week.getYear())
        .nextWeekNumber(week.getWeekNumber() + 1)
        .nextYear(week.getYear())
        .build();
    assertThat(subject.execute(week)).isEqualTo(weekNav);
  }

  @Test
  public void execute_handlesEndOfYear() {
    Week week = Week.builder()
        .year(2018)
        .weekNumber(52)
        .build();
    WeekNav weekNav = WeekNav.builder()
        .prevWeekNumber(week.getWeekNumber() - 1)
        .prevYear(week.getYear())
        .nextWeekNumber(1)
        .nextYear(week.getYear() + 1)
        .build();

    assertThat(subject.execute(week)).isEqualTo(weekNav);
  }

  @Test
  public void execute_handlesBeginningOfYear() {
    Week week = Week.builder()
        .year(2018)
        .weekNumber(1)
        .build();
    WeekNav weekNav = WeekNav.builder()
        .prevWeekNumber(52)
        .prevYear(week.getYear() - 1)
        .nextWeekNumber(week.getWeekNumber() + 1)
        .nextYear(week.getYear())
        .build();

    assertThat(subject.execute(week)).isEqualTo(weekNav);
  }
}