package com.tarbadev.witchcraft.weeks.domain.entity;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
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
