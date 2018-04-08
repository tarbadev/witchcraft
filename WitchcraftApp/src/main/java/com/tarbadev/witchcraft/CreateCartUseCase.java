package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateCartUseCase {
  private final DatabaseCartRepository databaseCartRepository;
  private final IngredientConverter ingredientConverter;

  public CreateCartUseCase(DatabaseCartRepository databaseCartRepository, IngredientConverter ingredientConverter) {
    this.databaseCartRepository = databaseCartRepository;
    this.ingredientConverter = ingredientConverter;
  }

  public Cart execute(List<Recipe> recipes) {
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(getItemsFromRecipe(recipes))
        .build();

    return databaseCartRepository.save(cart);
  }

  private List<Item> getItemsFromRecipe(List<Recipe> recipes) {
    List<Ingredient> allIngredients = recipes.stream()
        .flatMap(recipe -> recipe.getIngredients().stream())
        .collect(Collectors.toList());

    Map<Object, List<Ingredient>> ingredientsByNameAndUnit =
        allIngredients.stream().collect(Collectors.groupingBy(ingredient ->
            Arrays.<Object>asList(ingredient.getName(), ingredient.getUnit()), Collectors.toList()));

    Map<String, List<Ingredient>> ingredientsByName = ingredientsByNameAndUnit.values().stream()
        .map(e -> e.stream().reduce((a, b) -> a.addQuantity(b.getQuantity())).orElse(null))
        .collect(Collectors.groupingBy(Ingredient::getName));

    List<Ingredient> ingredients = ingredientsByName.entrySet().stream()
        .map(entrySet -> entrySet.getValue().stream()
            .reduce(ingredientConverter::addToHighestUnit)
            .orElse(null)
        )
        .collect(Collectors.toList());

    return ingredients.stream()
        .map(ingredient -> Item.builder()
            .name(ingredient.getName())
            .quantity(ingredient.getQuantity())
            .unit(ingredient.getUnit())
            .build())
        .collect(Collectors.toList());
  }
}
