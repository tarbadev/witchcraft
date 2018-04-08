package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IngredientConverter {
  private final UnitConverter unitConverter;

  public IngredientConverter(UnitConverter unitConverter) {
    this.unitConverter = unitConverter;
  }

  public Ingredient addToHighestUnit(Ingredient ingredient1, Ingredient ingredient2) {
    Map.Entry<String, Double> highestUnit = unitConverter.convertToHighestUnit(ingredient1.getQuantity(), ingredient1.getUnit(), ingredient2.getUnit());
    if (highestUnit.getKey().equals(ingredient1.getUnit())) {
      Double result = unitConverter.convert(ingredient2.getQuantity(), ingredient2.getUnit(), ingredient1.getUnit());
      return ingredient1.addQuantity(
          result
      );
    } else
      return Ingredient.builder()
          .name(ingredient1.getName())
          .unit(highestUnit.getKey())
          .quantity(highestUnit.getValue())
          .build()
          .addQuantity(ingredient2.getQuantity());
  }
}
