package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import com.tarbadev.witchcraft.domain.usecase.*;
import com.tarbadev.witchcraft.web.RecipeFormRequest;
import com.tarbadev.witchcraft.web.RecipeModifyForm;
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
  private DeleteRecipeUseCase deleteRecipeUseCase;
  private DoesRecipeExistUseCase doesRecipeExistUseCase;
  private SetFavoriteRecipeUseCase setFavoriteRecipeUseCase;
  private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  private SaveRecipeUseCase saveRecipeUseCase;
  private GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase;
  private GetFavoriteRecipesUseCase getFavoriteRecipesUseCase;
  private LastAddedRecipesUseCase lastAddedRecipesUseCase;

  @Autowired
  public RecipesRestController(
      RecipeCatalogUseCase recipeCatalogUseCase,
      GetRecipeUseCase getRecipeUseCase,
      DeleteRecipeUseCase deleteRecipeUseCase,
      DoesRecipeExistUseCase doesRecipeExistUseCase,
      SetFavoriteRecipeUseCase setFavoriteRecipeUseCase,
      GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase,
      SaveRecipeUseCase saveRecipeUseCase,
      GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase,
      GetFavoriteRecipesUseCase getFavoriteRecipesUseCase, LastAddedRecipesUseCase lastAddedRecipesUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
    this.deleteRecipeUseCase = deleteRecipeUseCase;
    this.doesRecipeExistUseCase = doesRecipeExistUseCase;
    this.setFavoriteRecipeUseCase = setFavoriteRecipeUseCase;
    this.getRecipeDetailsFromUrlUseCase = getRecipeDetailsFromUrlUseCase;
    this.saveRecipeUseCase = saveRecipeUseCase;
    this.getRecipeDetailsFromFormUseCase = getRecipeDetailsFromFormUseCase;
    this.getFavoriteRecipesUseCase = getFavoriteRecipesUseCase;
    this.lastAddedRecipesUseCase = lastAddedRecipesUseCase;
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
  public Recipe importFromUrl(@RequestBody RecipeFormRequest recipeFormRequest) {
    Recipe recipe = getRecipeDetailsFromUrlUseCase.execute(recipeFormRequest.getUrl());

    return saveRecipeUseCase.execute(recipe);
  }

  @PostMapping("/importFromForm")
  public Recipe importFromForm(@RequestBody RecipeFormRequest recipeFormRequest) {
    Recipe recipe = getRecipeDetailsFromFormUseCase.execute(
        recipeFormRequest.getName(),
        recipeFormRequest.getUrl(),
        recipeFormRequest.getIngredients(),
        recipeFormRequest.getSteps(),
        recipeFormRequest.getImageUrl()
    );

    return saveRecipeUseCase.execute(recipe);
  }

  @PutMapping("/{id}/update")
  public Recipe update(@RequestBody RecipeModifyForm recipeModifyForm) {
    Recipe recipe = Recipe.builder()
        .id(recipeModifyForm.getId())
        .name(recipeModifyForm.getName())
        .originUrl(recipeModifyForm.getUrl())
        .imgUrl(recipeModifyForm.getImgUrl())
        .favorite(recipeModifyForm.getFavorite())
        .ingredients(recipeModifyForm.getIngredients().stream()
            .map(ingredientModifyForm -> Ingredient.builder()
                .id(ingredientModifyForm.getId())
                .name(ingredientModifyForm.getName())
                .unit(ingredientModifyForm.getUnit())
                .quantity(ingredientModifyForm.getQuantity())
                .build()
            ).collect(Collectors.toList())
        )
        .steps(recipeModifyForm.getSteps().stream()
            .map(stepModifyForm -> Step.builder()
                .id(stepModifyForm.getId())
                .name(stepModifyForm.getName())
                .build()
            )
            .collect(Collectors.toList())
        )
        .build();

    return saveRecipeUseCase.execute(recipe);
  }

  @GetMapping("/favorites")
  public List<Recipe> favorites() {
    return getFavoriteRecipesUseCase.execute();
  }

  @GetMapping("/latest")
  public List<Recipe> latest() {
    return lastAddedRecipesUseCase.execute();
  }
}
