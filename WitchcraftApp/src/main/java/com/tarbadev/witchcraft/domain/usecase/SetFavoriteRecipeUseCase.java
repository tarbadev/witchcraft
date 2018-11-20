package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class SetFavoriteRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public SetFavoriteRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public void execute(int id, boolean favorite) {
    recipeRepository.setFavorite(id, favorite);
  }
}
