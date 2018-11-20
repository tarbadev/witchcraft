package com.tarbadev.witchcraft.domain.entity;

import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Week {
  private Integer id;
  private Integer year;
  private Integer weekNumber;
  private List<Day> days;
}
