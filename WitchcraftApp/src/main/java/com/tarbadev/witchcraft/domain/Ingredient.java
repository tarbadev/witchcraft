package com.tarbadev.witchcraft.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
=======
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
>>>>>>> Stashed changes

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

  public Ingredient addQuantity(Double quantity) {
<<<<<<< Updated upstream
    this.quantity += quantity;
    return this;
=======
    return Ingredient.builder()
        .id(this.id)
        .name(this.name)
        .quantity(this.quantity + quantity)
        .unit(this.unit)
        .build();
  }

  public static Ingredient getIngredientFromString(String text) {
    Pattern pattern = Pattern.compile("(\\d+\\/\\d+|\\d+)");
    Matcher matcher = pattern.matcher(text);
    if (matcher.find())
    {
      System.out.println("matcher = " + matcher);
      System.out.println(matcher.group(1));
    }

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
>>>>>>> Stashed changes
  }
}
