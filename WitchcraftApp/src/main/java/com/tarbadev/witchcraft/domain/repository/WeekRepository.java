package com.tarbadev.witchcraft.domain.repository;

import com.tarbadev.witchcraft.domain.entity.Week;

public interface WeekRepository {
  Week findByYearAndWeekNumber(int year, int weekNumber);
  void save(Week week);
}
