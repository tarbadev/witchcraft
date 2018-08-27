package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.*;

import java.util.stream.Collectors;

public class EntityToDomain {
  public static Recipe recipeMapper(RecipeEntity recipeEntity) {
    return Recipe.builder()
        .id(recipeEntity.getId())
        .name(recipeEntity.getName().toLowerCase())
        .originUrl(recipeEntity.getOriginUrl())
        .imgUrl(recipeEntity.getImgUrl())
        .rating(recipeEntity.getRating())
        .ingredients(recipeEntity.getIngredients().stream().map(EntityToDomain::ingredientMapper).collect(Collectors.toList()))
        .steps(recipeEntity.getSteps().stream().map(EntityToDomain::stepMapper).collect(Collectors.toList()))
        .build();
  }

  public static Ingredient ingredientMapper(IngredientEntity ingredientEntity) {
    return Ingredient.builder()
        .id(ingredientEntity.getId())
        .name(ingredientEntity.getName())
        .quantity(ingredientEntity.getQuantity())
        .unit(ingredientEntity.getUnit())
        .build();
  }

  public static Step stepMapper(StepEntity stepEntity) {
    return Step.builder()
        .id(stepEntity.getId())
        .name(stepEntity.getName())
        .build();
  }

  public static Cart cartMapper(CartEntity cartEntity) {
    return Cart.builder()
        .id(cartEntity.getId())
        .createdAt(cartEntity.getCreatedAt())
        .recipes(cartEntity.getRecipes().stream().map(EntityToDomain::recipeMapper).collect(Collectors.toList()))
        .items(cartEntity.getItems().stream().map(EntityToDomain::itemMapper).collect(Collectors.toList()))
        .build();
  }

  public static Item itemMapper(ItemEntity itemEntity) {
    return Item.builder()
        .id(itemEntity.getId())
        .name(itemEntity.getName())
        .quantity(itemEntity.getQuantity())
        .unit(itemEntity.getUnit())
        .build();
  }

  public static Week weekMapper(WeekEntity weekEntity) {
    return Week.builder()
        .id(weekEntity.getId())
        .weekNumber(weekEntity.getWeekNumber())
        .year(weekEntity.getYear())
        .days(weekEntity.getDays().stream().map(EntityToDomain::dayMapper).collect(Collectors.toList()))
        .build();
  }

  public static Day dayMapper(DayEntity dayEntity) {
    return Day.builder()
        .id(dayEntity.getId())
        .name(DayName.valueOf(dayEntity.getName()))
        .lunch(dayEntity.getLunch() != null ? recipeMapper(dayEntity.getLunch()) : null)
        .diner(dayEntity.getDiner() != null ? recipeMapper(dayEntity.getDiner()) : null)
        .build();
  }
}
