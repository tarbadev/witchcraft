package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipesRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private GetRecipeUseCase getRecipeUseCase;
  private RateRecipeUseCase rateRecipeUseCase;
  private DeleteRecipeUseCase deleteRecipeUseCase;
  private DoesRecipeExistUseCase doesRecipeExistUseCase;

  @Autowired
  public RecipesRestController(RecipeCatalogUseCase recipeCatalogUseCase, GetRecipeUseCase getRecipeUseCase, RateRecipeUseCase rateRecipeUseCase, DeleteRecipeUseCase deleteRecipeUseCase, DoesRecipeExistUseCase doesRecipeExistUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
    this.rateRecipeUseCase = rateRecipeUseCase;
    this.deleteRecipeUseCase = deleteRecipeUseCase;
    this.doesRecipeExistUseCase = doesRecipeExistUseCase;
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

  @DeleteMapping("/{id}")
  public ResponseEntity deleteRecipe(@PathVariable Integer id) {
    if (!doesRecipeExistUseCase.execute(id)) {
      return ResponseEntity.notFound().build();
    }

    deleteRecipeUseCase.execute(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
