package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.domain.entity.Day;
import com.tarbadev.witchcraft.domain.entity.DayName;
import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.repository.WeekRepository;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class WeekFromYearAndWeekNumberUseCaseTest {
  @Mock private WeekRepository weekRepository;

  private WeekFromYearAndWeekNumberUseCase subject;

  @Before
  public void setUp() {
    subject = new WeekFromYearAndWeekNumberUseCase(weekRepository);
  }

  @Test
  public void execute() {
    Week week = Week.builder()
        .weekNumber(12)
        .year(2018)
        .build();

    given(weekRepository.findByYearAndWeekNumber(week.getYear(), week.getWeekNumber())).willReturn(week);

    assertThat(subject.execute(week.getYear(), week.getWeekNumber())).isEqualTo(week);
  }

  @Test
  public void execute_databaseNullReturnsNewWeek() {
    int year = 2018;
    int weekNumber = 12;
    Week week = Week.builder()
        .year(year)
        .weekNumber(weekNumber)
        .days(Arrays.asList(
            Day.builder()
                .name(DayName.MONDAY)
                .build(),
            Day.builder()
                .name(DayName.TUESDAY)
                .build(),
            Day.builder()
                .name(DayName.WEDNESDAY)
                .build(),
            Day.builder()
                .name(DayName.THURSDAY)
                .build(),
            Day.builder()
                .name(DayName.FRIDAY)
                .build(),
            Day.builder()
                .name(DayName.SATURDAY)
                .build(),
            Day.builder()
                .name(DayName.SUNDAY)
                .build()
        ))
        .build();

    given(weekRepository.findByYearAndWeekNumber(year, weekNumber)).willReturn(null);

    assertThat(subject.execute(year, weekNumber)).isEqualTo(week);
  }
}