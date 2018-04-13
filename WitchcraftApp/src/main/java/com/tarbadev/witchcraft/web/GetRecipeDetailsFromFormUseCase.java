package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.RecipeRepository;
import com.tarbadev.witchcraft.domain.Step;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class GetRecipeDetailsFromFormUseCase {
  private final RecipeRepository recipeRepository;

  public GetRecipeDetailsFromFormUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Recipe execute(String name, String url, String ingredients, String steps) {
    Recipe recipe = Recipe.builder()
        .name(name)
        .url(url)
        .ingredients(Arrays.stream(ingredients.split("\n"))
            .map(Ingredient::getIngredientFromString)
            .collect(Collectors.toList()))
        .steps(Arrays.stream(steps.split("\n"))
            .map(step -> Step.builder().name(step).build())
            .collect(Collectors.toList()))
        .build();
    return recipeRepository.createRecipe(recipe);
  }
}
