package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Integer> {
  Week findByYearAndWeekNumber(Integer year, Integer weekNumber);
}
