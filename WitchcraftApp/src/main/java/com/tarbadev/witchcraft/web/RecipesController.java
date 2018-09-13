package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import com.tarbadev.witchcraft.domain.usecase.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipesController {
  private SaveRecipeUseCase saveRecipeUseCase;
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  private GetRecipeUseCase getRecipeUseCase;
  private GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase;
  private DeleteRecipeUseCase deleteRecipeUseCase;
  private SetFavoriteRecipeUseCase setFavoriteRecipeUseCase;

  public RecipesController(
      SaveRecipeUseCase saveRecipeUseCase,
      RecipeCatalogUseCase recipeCatalogUseCase,
      GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase,
      GetRecipeUseCase getRecipeUseCase,
      GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase,
      DeleteRecipeUseCase deleteRecipeUseCase,
      SetFavoriteRecipeUseCase setFavoriteRecipeUseCase) {
    this.saveRecipeUseCase = saveRecipeUseCase;
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeDetailsFromUrlUseCase = getRecipeDetailsFromUrlUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
    this.getRecipeDetailsFromFormUseCase = getRecipeDetailsFromFormUseCase;
    this.deleteRecipeUseCase = deleteRecipeUseCase;
    this.setFavoriteRecipeUseCase = setFavoriteRecipeUseCase;
  }

  @GetMapping("/recipes")
  public String index(Model model) {
    List<Recipe> recipes = recipeCatalogUseCase.execute();

    model.addAttribute("recipes", recipes);

    return "recipes/index";
  }

  @GetMapping("/recipes/{id}")
  public String show(@PathVariable Integer id, Model model) {
    model.addAttribute("recipe", getRecipeUseCase.execute(id));
    return "recipes/show";
  }

  @GetMapping("/recipes/new")
  public String newRecipe(RecipeUrlForm recipeUrlForm, RecipeManualForm recipeManualForm) {
    return "recipes/newRecipe";
  }

  @PostMapping("/recipes/importFromUrl")
  public String addRecipe(@Valid RecipeUrlForm recipeUrlForm) {
    Recipe recipe = getRecipeDetailsFromUrlUseCase.execute(recipeUrlForm.getOriginUrl());
    saveRecipeUseCase.execute(recipe);

    return "redirect:/recipes";
  }

  @PostMapping("/recipes/importFromForm")
  public String addRecipe(@Valid RecipeManualForm recipeManualForm) {
    Recipe recipe = getRecipeDetailsFromFormUseCase.execute(
        recipeManualForm.getName(),
        recipeManualForm.getOriginUrl(),
        recipeManualForm.getIngredients(),
        recipeManualForm.getSteps(),
        recipeManualForm.getImgUrl());
    saveRecipeUseCase.execute(recipe);

    return "redirect:/recipes";
  }

  @DeleteMapping("/recipes/{id}")
  public String deleteRecipe(@PathVariable Integer id) {
    deleteRecipeUseCase.execute(id);
    return "redirect:/recipes";
  }

  @PatchMapping("/recipes/{id}/rate/{rating}")
  public String rate(@PathVariable Integer id, @PathVariable Double rating) {
    setFavoriteRecipeUseCase.execute(id, true);
    return "redirect:/recipes/" + id;
  }

  @GetMapping("/recipes/{id}/modify")
  public String showModify(@PathVariable Integer id, Model model) {
    model.addAttribute("recipe", getRecipeUseCase.execute(id));
    return "recipes/modify";
  }

  @PatchMapping("/recipes/{id}/modify")
  public String modify(@PathVariable Integer id, @Valid RecipeModifyForm recipeModifyForm) {
    saveRecipeUseCase.execute(Recipe.builder()
        .id(recipeModifyForm.getId())
        .name(recipeModifyForm.getName())
        .originUrl(recipeModifyForm.getUrl())
        .imgUrl(recipeModifyForm.getImgUrl())
        .favorite(recipeModifyForm.getFavorite())
        .ingredients(recipeModifyForm.getIngredients().stream()
            .map(ingredientModifyForm -> Ingredient.builder()
            .id(ingredientModifyForm.getId())
            .name(ingredientModifyForm.getName())
            .quantity(ingredientModifyForm.getQuantity())
            .unit(ingredientModifyForm.getUnit())
            .build())
        .collect(Collectors.toList()))
        .steps(recipeModifyForm.getSteps().stream()
            .map(stepModifyForm -> Step.builder()
                .id(stepModifyForm.getId())
                .name(stepModifyForm.getName())
                .build())
            .collect(Collectors.toList()))
        .build());

    return "redirect:/recipes/" + id;
  }
}