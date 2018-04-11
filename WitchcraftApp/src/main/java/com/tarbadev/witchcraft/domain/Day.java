package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Day {
  private Integer id;
  private DayName name;
  private Recipe lunch;
  private Recipe diner;
}
