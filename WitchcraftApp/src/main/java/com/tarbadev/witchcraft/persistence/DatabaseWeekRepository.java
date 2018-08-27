package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.repository.WeekRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseWeekRepository implements WeekRepository {
  private final WeekEntityRepository weekEntityRepository;

  public DatabaseWeekRepository(WeekEntityRepository weekEntityRepository) {
    this.weekEntityRepository = weekEntityRepository;
  }

  @Override
  public Week findByYearAndWeekNumber(int year, int weekNumber) {
    return weekEntityRepository.findByYearAndWeekNumber(year, weekNumber).map(EntityToDomain::weekMapper).orElse(null);
  }

  @Override
  public void save(Week week) {
    weekEntityRepository.save(DomainToEntity.weekEntityMapper(week));
  }
}
