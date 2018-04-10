package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.DatabaseWeekRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;

@Component
public class GetCurrentWeekUseCase {
  private final DatabaseWeekRepository databaseWeekRepository;

  public GetCurrentWeekUseCase(DatabaseWeekRepository databaseWeekRepository) {
    this.databaseWeekRepository = databaseWeekRepository;
  }

  public Week execute() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

    Week week = databaseWeekRepository.findByYearAndWeekNumber(year, weekNumber);
    if (week == null) {
      week = Week.builder()
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
