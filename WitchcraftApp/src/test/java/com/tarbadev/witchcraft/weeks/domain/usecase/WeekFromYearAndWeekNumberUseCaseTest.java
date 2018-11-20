package com.tarbadev.witchcraft.weeks.domain.usecase;

import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import com.tarbadev.witchcraft.weeks.domain.entity.Day;
import com.tarbadev.witchcraft.weeks.domain.entity.DayName;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.weeks.domain.entity.Week;
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository;
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
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.TUESDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.WEDNESDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.THURSDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.FRIDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.SATURDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build(),
            Day.builder()
                .name(DayName.SUNDAY)
                .lunch(Recipe.builder().build())
                .diner(Recipe.builder().build())
                .build()
        ))
        .build();

    given(weekRepository.findByYearAndWeekNumber(year, weekNumber)).willReturn(null);

    assertThat(subject.execute(year, weekNumber)).isEqualTo(week);
  }
}