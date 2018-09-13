package com.tarbadev.witchcraft.persistence.helpers;

import com.tarbadev.witchcraft.domain.entity.*;
import com.tarbadev.witchcraft.persistence.entity.*;
import com.tarbadev.witchcraft.persistence.repository.IngredientEntity;

import java.util.stream.Collectors;

public class DomainToEntity {
  public static RecipeEntity recipeEntityMapper(Recipe recipe) {
    return RecipeEntity.builder()
        .id(recipe.getId())
        .name(recipe.getName())
        .originUrl(recipe.getOriginUrl())
        .imgUrl(recipe.getImgUrl())
        .favorite(recipe.getFavorite())
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

  public static WeekEntity weekEntityMapper(Week week) {
    return WeekEntity.builder()
        .id(week.getId())
        .year(week.getYear())
        .weekNumber(week.getWeekNumber())
        .days(week.getDays().stream()
            .map(day -> DayEntity.builder()
                .id(day.getId())
                .name(day.getName().toString())
                .lunch(day.getLunch() != null ? recipeEntityMapper(day.getLunch()) : null)
                .diner(day.getDiner() != null ? recipeEntityMapper(day.getDiner()) : null)
                .build())
            .collect(Collectors.toList()))
        .build();
  }
}
