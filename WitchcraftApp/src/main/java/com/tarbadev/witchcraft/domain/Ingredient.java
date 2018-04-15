package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Ingredient {
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

  public Ingredient addQuantity(Double quantity) {
    return Ingredient.builder()
        .id(this.id)
        .name(this.name)
        .quantity(this.quantity + quantity)
        .unit(this.unit)
        .build();
  }
}
