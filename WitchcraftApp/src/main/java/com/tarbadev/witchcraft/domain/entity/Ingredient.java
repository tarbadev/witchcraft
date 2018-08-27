package com.tarbadev.witchcraft.domain.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Ingredient {
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

  public Ingredient addQuantity(Double quantity) {
    return this.toBuilder()
        .quantity(this.quantity + quantity)
        .build();
  }
}
