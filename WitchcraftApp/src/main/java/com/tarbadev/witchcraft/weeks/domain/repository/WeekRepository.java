package com.tarbadev.witchcraft.weeks.domain.repository;

import com.tarbadev.witchcraft.weeks.domain.entity.Week;

public interface WeekRepository {
  Week findByYearAndWeekNumber(int year, int weekNumber);
  Week save(Week week);
}
