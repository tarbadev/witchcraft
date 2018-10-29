package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.*;
import com.tarbadev.witchcraft.web.RecipeUrlForm;
import com.tarbadev.witchcraft.web.RecipeUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipesRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private GetRecipeUseCase getRecipeUseCase;
  private DeleteRecipeUseCase deleteRecipeUseCase;
  private DoesRecipeExistUseCase doesRecipeExistUseCase;
  private SetFavoriteRecipeUseCase setFavoriteRecipeUseCase;
  private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  private SaveRecipeUseCase saveRecipeUseCase;

  @Autowired
  public RecipesRestController(
      RecipeCatalogUseCase recipeCatalogUseCase,
      GetRecipeUseCase getRecipeUseCase,
      DeleteRecipeUseCase deleteRecipeUseCase,
      DoesRecipeExistUseCase doesRecipeExistUseCase,
      SetFavoriteRecipeUseCase setFavoriteRecipeUseCase,
      GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase, SaveRecipeUseCase saveRecipeUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
    this.deleteRecipeUseCase = deleteRecipeUseCase;
    this.doesRecipeExistUseCase = doesRecipeExistUseCase;
    this.setFavoriteRecipeUseCase = setFavoriteRecipeUseCase;
    this.getRecipeDetailsFromUrlUseCase = getRecipeDetailsFromUrlUseCase;
    this.saveRecipeUseCase = saveRecipeUseCase;
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
  public Recipe show(@PathVariable("id") Integer id) {
    return getRecipeUseCase.execute(id);
  }

  @PatchMapping("/{id}")
  public ResponseEntity setFavorite(@PathVariable("id") Integer id, @RequestBody Map<String, String> requestParams) {
    boolean favorite = Boolean.parseBoolean(requestParams.get("favorite"));
    setFavoriteRecipeUseCase.execute(id, favorite);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteRecipe(@PathVariable Integer id) {
    if (!doesRecipeExistUseCase.execute(id)) {
      return ResponseEntity.notFound().build();
    }

    deleteRecipeUseCase.execute(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }



  @PostMapping("/importFromUrl")
  public Recipe addRecipe(@Valid @RequestBody RecipeUrlRequest recipeUrlRequest) {
    Recipe recipe = getRecipeDetailsFromUrlUseCase.execute(recipeUrlRequest.getUrl());

    return saveRecipeUseCase.execute(recipe);
  }
}
