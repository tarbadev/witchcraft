package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

@Component
public class IngredientFromStringUseCase {
  private static final List<String> SUPPORTED_UNITS = Arrays.asList(
      "lb",
      "oz",
      "tsp",
      "tbsp",
      "cup"
  );

  public Ingredient execute(String text) {
    text = text
        .replace("½", "1/2")
        .replace("⅓", "1/3")
        .replace("⅔", "2/3")
        .replace("¼", "1/4")
        .replace("¾", "3/4")
        .replace("⅛", "1/8")
        .replace("pound", "lb")
        .replace("ounce", "oz")
        .replace("teaspoon", "tsp")
        .replace("tablespoon", "tbsp");
    Double quantity = 0.0;
    String unit = "";
    String name = "";
    Pattern pattern = Pattern.compile("([\\d ]*\\d+[[\\⁄\\/]\\d]*|\\d+|)([\\s]|)(([A-Za-z]+?|[A-Za-z]+?)(s\\b|\\b)|)([ .,]*)([()\\w ,-\\.\\/&\\'!\\+]+)");
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
          } else if (number.contains("⁄")) {
            String[] fraction = number.split("⁄");
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

      return Ingredient.builder()
          .name(name)
          .quantity(quantity)
          .unit(unit)
          .build();
    } else {
      System.err.println("Ingredient not supported: " + text);

      return null;
    }
  }
}
