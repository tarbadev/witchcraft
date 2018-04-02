package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CreateCartUseCase {
  private DatabaseCartRepository databaseCartRepository;

  public CreateCartUseCase(DatabaseCartRepository databaseCartRepository) {
    this.databaseCartRepository = databaseCartRepository;
  }

  public Cart execute(List<Recipe> recipes) {
    List<Item> items = recipes.stream()
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

    Map<Object, List<Item>> map =
        items.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    items = map.values().stream()
        .map(e -> e.stream().reduce((a, b) -> a.addQuantity(b.getQuantity())).orElse(null))
        .sorted(Comparator.comparing(Item::getName))
        .collect(Collectors.toList());

    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(items)
        .build();

    return databaseCartRepository.save(cart);
  }
}
