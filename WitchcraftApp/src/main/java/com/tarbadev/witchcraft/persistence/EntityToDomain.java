package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Recipe;

public class EntityToDomain {
  public static Recipe recipeMapper(RecipeEntity recipeEntity) {
    return Recipe.builder()
        .id(recipeEntity.getId())
        .name(recipeEntity.getName())
        .url(recipeEntity.getUrl())
        .imgUrl(recipeEntity.getImgUrl())
        .ingredients(recipeEntity.getIngredients())
        .steps(recipeEntity.getSteps())
        .build();
  }
}
