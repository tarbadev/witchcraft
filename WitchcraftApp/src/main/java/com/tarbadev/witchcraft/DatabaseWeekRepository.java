package com.tarbadev.witchcraft;

import org.springframework.stereotype.Repository;

@Repository
public class DatabaseWeekRepository {
  private final WeekRepository weekRepository;

  public DatabaseWeekRepository(WeekRepository weekRepository) {
    this.weekRepository = weekRepository;
  }

  public Week findByYearAndWeekNumber(int year, int weekNumber) {
    return weekRepository.findByYearAndWeekNumber(year, weekNumber);
  }
}
