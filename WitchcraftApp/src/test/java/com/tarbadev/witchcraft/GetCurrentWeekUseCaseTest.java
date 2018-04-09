package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetCurrentWeekUseCaseTest {
  private GetCurrentWeekUseCase subject;

  @Autowired private DatabaseWeekRepository databaseWeekRepository;

  @Before
  public void setUp() {
    subject = new GetCurrentWeekUseCase(databaseWeekRepository);
  }

  @Test
  public void execute() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    Week week = Week.builder().build();

    given(databaseWeekRepository.findByYearAndWeekNumber(year, weekNumber)).willReturn(week);

    assertThat(subject.execute()).isEqualTo(week);
  }

  @Test
  public void execute_databaseNullReturnsNewWeek() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    Week week = Week.builder()
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

    given(databaseWeekRepository.findByYearAndWeekNumber(year, weekNumber)).willReturn(null);

    assertThat(subject.execute()).isEqualTo(week);
  }
}