package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class DeleteRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public DeleteRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public void execute(int id) {
    recipeRepository.delete(id);
  }
}
