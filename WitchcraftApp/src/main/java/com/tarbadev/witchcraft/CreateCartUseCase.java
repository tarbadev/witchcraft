package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CreateCartUseCase {
  private final DatabaseCartRepository databaseCartRepository;
  private final UnitConverter unitConverter;

  public CreateCartUseCase(DatabaseCartRepository databaseCartRepository, UnitConverter unitConverter) {
    this.databaseCartRepository = databaseCartRepository;
    this.unitConverter = unitConverter;
  }

  public Cart execute(List<Recipe> recipes) {
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(getItemsFromRecipe(recipes))
        .build();

    return databaseCartRepository.save(cart);
  }

  private List<Item> getItemsFromRecipe(List<Recipe> recipes) {
    List<Item> allItems = recipes.stream()
        .flatMap(recipe -> recipe.getIngredients().stream()
            .map(ingredient -> Item.builder()
                .name(ingredient.getName())
                .unit(ingredient.getUnit())
                .quantity(ingredient.getQuantity())
                .build()
            )
        )
        .collect(Collectors.toList());

    Function<Item, List<Object>> compositeKey = item ->
        Arrays.<Object>asList(item.getName(), item.getUnit());

    Map<Object, List<Item>> itemsByNameAndUnit =
        allItems.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    Map<String, List<Item>> itemsByName = itemsByNameAndUnit.values().stream()
        .map(e -> e.stream().reduce((a, b) -> a.addQuantity(b.getQuantity())).orElse(null))
        .collect(Collectors.groupingBy(Item::getName));

    return getItemsWithTotalQuantity(itemsByName);
  }

  private List<Item> getItemsWithTotalQuantity(Map<String, List<Item>> itemsByName) {
    List<Item> items = itemsByName.entrySet().stream()
        .map(entrySet -> entrySet.getValue().stream()
            .reduce((item1, item2) -> getTotalItem(item1, item2))
            .orElse(null)
        )
        .collect(Collectors.toList());
    return items;
  }

  private Item getTotalItem(Item item1, Item item2) {
    return null;
  }
}
