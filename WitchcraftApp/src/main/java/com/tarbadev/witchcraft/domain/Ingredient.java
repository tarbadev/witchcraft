package com.tarbadev.witchcraft.domain;

import lombok.*;

import java.util.Arrays;
import java.util.List;

import static java.lang.Double.parseDouble;

@Value
@Builder
public class Ingredient {
  private static final List<String> SUPPORTED_UNITS = Arrays.asList(
      "lb",
      "oz",
      "tsp",
      "tbsp",
      "cup",
      "cups"
  );

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

  public static Ingredient getIngredientFromString(String text) {
    Double quantity = 0.0;
    String unit = "";
    String name = "";
    String[] words = text.split(" ");

    boolean quantityFound = false;

    for (String word : words) {
      if (!quantityFound) {
        Double tempQuantity = null;
        try {
          tempQuantity = parseDouble(word);
        } catch (NumberFormatException e) {
        }

        if (tempQuantity != null)
          quantity += tempQuantity;
        else if (word.contains("/")) {
          String[] fraction = word.split("/");
          tempQuantity = parseDouble(fraction[0]) / parseDouble(fraction[1]);
          quantity += tempQuantity;
        } else
          quantityFound = true;
      }

      if (quantityFound) {
        String tempUnit = word
            .toLowerCase()
            .replaceAll("\\.", "");
        if (SUPPORTED_UNITS.contains(tempUnit)) {
          unit = tempUnit;

          if (unit.equals("cups"))
            unit = "cup";
        } else {
          name = text.substring(text.indexOf(word));
          break;
        }
      }
    }

    return Ingredient.builder()
        .name(name)
        .quantity(quantity)
        .unit(unit)
        .build();
  }
}
