package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.GetRecipeUseCase;
import com.tarbadev.witchcraft.domain.usecase.RecipeCatalogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipesRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private GetRecipeUseCase getRecipeUseCase;

  @Autowired
  public RecipesRestController(RecipeCatalogUseCase recipeCatalogUseCase, GetRecipeUseCase getRecipeUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
  }

  @GetMapping
  public Map<String, List<Recipe>> list() {
    Map<String, List<Recipe>> returnMap = new HashMap<>();
    List<Recipe> recipeList = recipeCatalogUseCase.execute();

    recipeList = recipeList.stream()
        .map(recipe -> recipe.toBuilder().url("/recipes/" + recipe.getId()).build())
        .collect(Collectors.toList());

    returnMap.put("recipes", recipeList);

    return returnMap;
  }

  @GetMapping("/{id}")
  public Recipe show(@PathVariable("id") String id) {
    return getRecipeUseCase.execute(Integer.parseInt(id));
  }
}
