package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.entity.DayName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayForm {
  private Integer id;
  private DayName name;
  private Integer lunchId;
  private Integer dinerId;
}
