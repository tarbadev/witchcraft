package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class DatabaseRecipeRepository {
  private RecipeRepository recipeRepository;

  public DatabaseRecipeRepository(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Recipe createRecipe(Recipe recipe) {
<<<<<<< Updated upstream
    return recipeRepository.saveAndFlush(recipe);
=======
    RecipeEntity recipeEntity = RecipeEntity.builder()
        .name(recipe.getName())
        .url(recipe.getUrl())
        .imgUrl(recipe.getImgUrl())
        .ingredients(recipe.getIngredients().stream().map(DomainToEntity::ingredientEntityMapper).collect(Collectors.toList()))
        .steps(recipe.getSteps().stream().map(DomainToEntity::stepEntityMapper).collect(Collectors.toList()))
        .build();
    System.out.println("recipeEntity = " + recipeEntity);
    return recipeMapper(recipeEntityRepository.saveAndFlush(recipeEntity));
>>>>>>> Stashed changes
  }

  public List<Recipe> findAll() {
    return recipeRepository.findAllByOrderByName();
  }

  public Recipe findById(Integer recipeId) {
    Recipe recipe = recipeRepository.findById(recipeId).get();
    recipe.getIngredients().sort(Comparator.comparing(Ingredient::getName));
    return recipe;
  }
}
