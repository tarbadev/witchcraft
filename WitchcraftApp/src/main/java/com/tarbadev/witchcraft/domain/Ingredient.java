package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

@Value
@Builder
public class Ingredient {
  private static final List<String> SUPPORTED_UNITS = Arrays.asList(
      "lb",
      "oz",
      "tsp",
      "tbsp",
      "cup"
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
    Pattern pattern = Pattern.compile("([\\d ]+\\/\\d+|\\d+|)([\\s]|)(([A-Za-z]+?|[A-Za-z]+?)(s\\b|\\b)|)([ .,])([()\\w ,-\\.\\/&\\']+)");
    Matcher matcher = pattern.matcher(text);
    if (matcher.find())
    {
      String quantityStr = matcher.group(1);
      if (!quantityStr.isEmpty()) {
        String[] numbers = quantityStr.split(" ");
        for (String number : numbers) {
          Double tempQuantity = null;
          try {
            tempQuantity = parseDouble(number);
          } catch (NumberFormatException e) {
          }

          if (tempQuantity != null)
            quantity += tempQuantity;
          else if (number.contains("/")) {
            String[] fraction = number.split("/");
            tempQuantity = parseDouble(fraction[0]) / parseDouble(fraction[1]);
            quantity += tempQuantity;
          }
        }
      }

      if (matcher.group(4) != null) {
        String tempUnit = matcher.group(4)
            .toLowerCase()
            .replaceAll("\\.", "");
        if (SUPPORTED_UNITS.contains(tempUnit)) {
          unit = tempUnit;
        } else if (!tempUnit.isEmpty()) {
          name = tempUnit + matcher.group(5) + matcher.group(6);
        }
      }

      name += matcher.group(7);
    } else {
      System.err.println("Ingredient not supported: " + text);
    }

    return Ingredient.builder()
        .name(name)
        .quantity(quantity)
        .unit(unit)
        .build();
  }
}
