package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class GetRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public GetRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Recipe execute(Integer recipeId) {
    return recipeRepository.findById(recipeId);
  }
}
