package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.Step;

import java.util.stream.Collectors;

public class EntityToDomain {
  public static Recipe recipeMapper(RecipeEntity recipeEntity) {
    return Recipe.builder()
        .id(recipeEntity.getId())
        .name(recipeEntity.getName())
        .url(recipeEntity.getUrl())
        .imgUrl(recipeEntity.getImgUrl())
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
}
