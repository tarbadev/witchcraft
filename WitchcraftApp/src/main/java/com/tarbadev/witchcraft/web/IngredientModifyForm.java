package com.tarbadev.witchcraft.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientModifyForm {
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;
}
