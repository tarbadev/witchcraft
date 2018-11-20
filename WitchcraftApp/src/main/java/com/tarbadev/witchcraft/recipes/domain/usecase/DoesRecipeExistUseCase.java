package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class DoesRecipeExistUseCase {
  private final RecipeRepository recipeRepository;

  public DoesRecipeExistUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public boolean execute(int id) {
    return recipeRepository.existsById(id);
  }
}
