package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
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
