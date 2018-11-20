package com.tarbadev.witchcraft.weeks.persistence.repository;

import com.tarbadev.witchcraft.weeks.domain.entity.Week;
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository;
import com.tarbadev.witchcraft.weeks.persistence.entity.WeekEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseWeekRepository implements WeekRepository {
  private final WeekEntityRepository weekEntityRepository;

  public DatabaseWeekRepository(WeekEntityRepository weekEntityRepository) {
    this.weekEntityRepository = weekEntityRepository;
  }

  @Override
  public Week findByYearAndWeekNumber(int year, int weekNumber) {
    return weekEntityRepository.findByYearAndWeekNumber(year, weekNumber).map(WeekEntity::week).orElse(null);
  }

  @Override
  public Week save(Week week) {
    return weekEntityRepository.saveAndFlush(new WeekEntity(week)).week();
  }
}
