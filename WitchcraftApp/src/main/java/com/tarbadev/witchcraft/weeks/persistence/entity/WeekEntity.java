package com.tarbadev.witchcraft.weeks.persistence.entity;

import com.tarbadev.witchcraft.weeks.domain.entity.Week;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "weeks")
public class WeekEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  private Integer year;
  private Integer weekNumber;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "week_id")
  private List<DayEntity> days;

  public WeekEntity(Week week) {
    id = week.getId();
        year = week.getYear();
    weekNumber = week.getWeekNumber();
        days = week.getDays().stream().map(DayEntity::new).collect(toList());
  }

  public Week week() {
    return Week.builder()
        .id(id)
        .weekNumber(weekNumber)
        .year(year)
        .days(days.stream().map(DayEntity::day).collect(toList()))
        .build();
  }
}
