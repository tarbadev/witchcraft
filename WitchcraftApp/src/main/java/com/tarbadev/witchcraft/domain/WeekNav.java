package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WeekNav {
  private Integer prevYear;
  private Integer prevWeekNumber;
  private Integer nextYear;
  private Integer nextWeekNumber;
}
