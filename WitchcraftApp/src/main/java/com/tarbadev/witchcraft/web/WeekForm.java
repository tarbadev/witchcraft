package com.tarbadev.witchcraft.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekForm {
  private Integer id;
  private Integer year;
  private List<DayForm> days;
}
