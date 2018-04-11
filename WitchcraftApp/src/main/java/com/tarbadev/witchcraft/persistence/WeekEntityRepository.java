package com.tarbadev.witchcraft.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeekEntityRepository extends JpaRepository<WeekEntity, Integer> {
  Optional<WeekEntity> findByYearAndWeekNumber(Integer year, Integer weekNumber);
}
