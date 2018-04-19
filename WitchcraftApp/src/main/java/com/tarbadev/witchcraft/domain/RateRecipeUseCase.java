package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class RateRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public RateRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public void execute(int id, double rating) {
    recipeRepository.rateRecipe(id, rating);
  }
}
