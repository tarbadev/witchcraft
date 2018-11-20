package com.tarbadev.witchcraft.domain.entity;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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
