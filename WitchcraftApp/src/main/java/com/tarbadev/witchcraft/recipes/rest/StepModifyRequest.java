package com.tarbadev.witchcraft.recipes.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StepModifyRequest {
  private Integer id;
  private String name;
}
