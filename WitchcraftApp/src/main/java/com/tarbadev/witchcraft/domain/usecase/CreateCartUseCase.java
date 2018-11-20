package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Item;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateCartUseCase {
  private final CartRepository cartRepository;
  private final IngredientConverter ingredientConverter;

  public CreateCartUseCase(CartRepository cartRepository, IngredientConverter ingredientConverter) {
    this.cartRepository = cartRepository;
    this.ingredientConverter = ingredientConverter;
  }

  public Cart execute(List<Recipe> recipes) {
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(getItemsFromRecipe(recipes))
        .build();

    return cartRepository.save(cart);
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

    System.out.println("Ingredients to calculate = " + ingredientsByName.values().stream()
        .filter(values -> values.size() > 1)
        .collect(Collectors.toList())
    );

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
