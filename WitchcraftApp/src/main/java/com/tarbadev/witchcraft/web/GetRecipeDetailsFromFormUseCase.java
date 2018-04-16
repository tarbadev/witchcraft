package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.IngredientFromStringUseCase;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.Step;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class GetRecipeDetailsFromFormUseCase {
  private IngredientFromStringUseCase ingredientFromStringUseCase;

  public GetRecipeDetailsFromFormUseCase(IngredientFromStringUseCase ingredientFromStringUseCase) {
    this.ingredientFromStringUseCase = ingredientFromStringUseCase;
  }

  public Recipe execute(String name, String url, String ingredients, String steps, String imgUrl) {
    return Recipe.builder()
        .name(name)
        .url(url)
        .imgUrl(imgUrl)
        .ingredients(Arrays.stream(ingredients.split("\n"))
            .map(ingredient -> ingredientFromStringUseCase.execute(ingredient))
            .collect(Collectors.toList()))
        .steps(Arrays.stream(steps.split("\n"))
            .map(step -> Step.builder().name(step).build())
            .collect(Collectors.toList()))
        .build();
  }
}
