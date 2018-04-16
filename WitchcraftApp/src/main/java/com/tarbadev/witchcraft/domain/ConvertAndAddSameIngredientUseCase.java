package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConvertAndAddSameIngredientUseCase {
  private final IngredientConverter ingredientConverter;

  public ConvertAndAddSameIngredientUseCase(IngredientConverter ingredientConverter) {
    this.ingredientConverter = ingredientConverter;
  }

  public List<Ingredient> execute(List<Ingredient> allIngredients) {
    Map<Object, List<Ingredient>> ingredientsByNameAndUnit =
        allIngredients.stream().collect(Collectors.groupingBy(ingredient ->
            Arrays.<Object>asList(ingredient.getName(), ingredient.getUnit()), Collectors.toList()));

    Map<String, List<Ingredient>> ingredientsByName = ingredientsByNameAndUnit.values().stream()
        .map(e -> e.stream().reduce((a, b) -> a.addQuantity(b.getQuantity())).orElse(null))
        .collect(Collectors.groupingBy(Ingredient::getName));

    return ingredientsByName.entrySet().stream()
        .map(entrySet -> entrySet.getValue().stream()
                  .reduce(ingredientConverter::addToHighestUnit)
                  .orElse(null)
        )
        .collect(Collectors.toList());
  }
}
