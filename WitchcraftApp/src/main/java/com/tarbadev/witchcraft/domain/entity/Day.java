package com.tarbadev.witchcraft.domain.entity;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Day {
  private Integer id;
  private DayName name;
  private Recipe lunch;
  private Recipe diner;
}
