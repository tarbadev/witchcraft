package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Step;

public class DomainToEntity {
  public static IngredientEntity ingredientEntityMapper(Ingredient ingredient) {
    return IngredientEntity.builder()
        .id(ingredient.getId())
        .name(ingredient.getName())
        .quantity(ingredient.getQuantity())
        .unit(ingredient.getUnit())
        .build();
  }

  public static StepEntity stepEntityMapper(Step step) {
    return StepEntity.builder()
        .id(step.getId())
        .name(step.getName())
        .build();
  }
}
