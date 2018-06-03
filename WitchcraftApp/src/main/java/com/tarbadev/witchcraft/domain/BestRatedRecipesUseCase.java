package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestRatedRecipesUseCase {
  private final RecipeRepository recipeRepository;

  public BestRatedRecipesUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<Recipe> execute() {
    return recipeRepository.findTopFiveRecipes();
  }
}
