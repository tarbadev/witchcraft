package com.tarbadev.witchcraft.domain;

public interface WeekRepository {
  Week findByYearAndWeekNumber(int year, int weekNumber);
}
