package com.tarbadev.witchcraft.persistence.repository;

import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.repository.WeekRepository;
import com.tarbadev.witchcraft.persistence.helpers.DomainToEntity;
import com.tarbadev.witchcraft.persistence.helpers.EntityToDomain;
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
  public Week save(Week week) {
    return EntityToDomain.weekMapper(
        weekEntityRepository.saveAndFlush(DomainToEntity.weekEntityMapper(week))
    );
  }
}
