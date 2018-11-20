package com.tarbadev.witchcraft.recipes.persistence.entity;

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IngredientEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

  public IngredientEntity(Ingredient ingredient) {
    id = ingredient.getId();
    name = ingredient.getName();
    quantity = ingredient.getQuantity();
    unit = ingredient.getUnit();
  }

  public Ingredient ingredient() {
    return Ingredient.builder()
        .id(id)
        .name(name)
        .quantity(quantity)
        .unit(unit)
        .build();
  }
}
