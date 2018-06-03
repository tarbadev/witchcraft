package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LastAddedRecipesUseCase {
  private final RecipeRepository recipeRepository;

  public LastAddedRecipesUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<Recipe> execute() {
    return recipeRepository.findLastAddedRecipes();
  }
}
