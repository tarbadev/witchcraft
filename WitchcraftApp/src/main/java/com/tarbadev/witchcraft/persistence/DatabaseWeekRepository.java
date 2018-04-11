package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Week;
import com.tarbadev.witchcraft.domain.WeekRepository;
import org.springframework.stereotype.Repository;

import static com.tarbadev.witchcraft.persistence.EntityToDomain.weekMapper;

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
}