package com.tarbadev.witchcraft;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Integer> {
  Week findByYearAndWeekNumber(Integer year, Integer weekNumber);
}
