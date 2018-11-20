package com.tarbadev.witchcraft.recipes.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientModifyRequest {
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;
}
