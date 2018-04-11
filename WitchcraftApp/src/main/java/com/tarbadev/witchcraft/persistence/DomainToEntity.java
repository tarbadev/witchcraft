package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.*;

import java.util.stream.Collectors;

public class DomainToEntity {
  public static RecipeEntity recipeEntityMapper(Recipe recipe) {
    return RecipeEntity.builder()
        .id(recipe.getId())
        .name(recipe.getName())
        .url(recipe.getUrl())
        .imgUrl(recipe.getImgUrl())
        .ingredients(recipe.getIngredients().stream().map(DomainToEntity::ingredientEntityMapper).collect(Collectors.toList()))
        .steps(recipe.getSteps().stream().map(DomainToEntity::stepEntityMapper).collect(Collectors.toList()))
        .build();
  }

  public static IngredientEntity ingredientEntityMapper(Ingredient ingredient) {
    return IngredientEntity.builder()
        .id(ingredient.getId())
        .name(ingredient.getName())
        .quantity(ingredient.getQuantity())
        .unit(ingredient.getUnit())
        .build();
  }

  public static StepEntity stepEntityMapper(Step step) {
    return StepEntity.builder()
        .id(step.getId())
        .name(step.getName())
        .build();
  }

  public static CartEntity cartEntityMapper(Cart cart) {
    return CartEntity.builder()
        .id(cart.getId())
        .createdAt(cart.getCreatedAt())
        .recipes(cart.getRecipes().stream().map(DomainToEntity::recipeEntityMapper).collect(Collectors.toList()))
        .items(cart.getItems().stream().map(DomainToEntity::itemEntityMapper).collect(Collectors.toList()))
        .build();
  }

  public static ItemEntity itemEntityMapper(Item item) {
    return ItemEntity.builder()
        .id(item.getId())
        .name(item.getName())
        .quantity(item.getQuantity())
        .unit(item.getUnit())
        .build();
  }
}