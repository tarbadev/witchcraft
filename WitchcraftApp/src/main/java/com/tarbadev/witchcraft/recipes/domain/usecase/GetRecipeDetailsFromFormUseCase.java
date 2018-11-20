package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.entity.Step;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetRecipeDetailsFromFormUseCase {
  private final IngredientFromStringUseCase ingredientFromStringUseCase;
  private final ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase;

  public GetRecipeDetailsFromFormUseCase(IngredientFromStringUseCase ingredientFromStringUseCase, ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase) {
    this.ingredientFromStringUseCase = ingredientFromStringUseCase;
    this.convertAndAddSameIngredientUseCase = convertAndAddSameIngredientUseCase;
  }

  public Recipe execute(String name, String url, String ingredients, String steps, String imgUrl) {
    return Recipe.builder()
        .name(name)
        .originUrl(url)
        .imgUrl(imgUrl)
        .ingredients(getIngredientsFromString(ingredients))
        .steps(Arrays.stream(steps.split("\n"))
            .map(step -> Step.builder().name(step).build())
            .collect(Collectors.toList()))
        .build();
  }

  private List<Ingredient> getIngredientsFromString(String ingredientsString) {
    return convertAndAddSameIngredientUseCase.execute(
        Arrays.stream(ingredientsString.split("\n"))
            .map(ingredient -> ingredientFromStringUseCase.execute(ingredient))
            .collect(Collectors.toList())
    );
  }
}
