package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipesController {
  private AddRecipeUseCase addRecipeUseCase;
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase;
  private GetRecipeUseCase getRecipeUseCase;
  private GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase;
  private DeleteRecipeUseCase deleteRecipeUseCase;

  public RecipesController(
      AddRecipeUseCase addRecipeUseCase,
      RecipeCatalogUseCase recipeCatalogUseCase,
      GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase,
      GetRecipeUseCase getRecipeUseCase,
      GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase,
      DeleteRecipeUseCase deleteRecipeUseCase
  ) {
    this.addRecipeUseCase = addRecipeUseCase;
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.getRecipeDetailsFromUrlUseCase = getRecipeDetailsFromUrlUseCase;
    this.getRecipeUseCase = getRecipeUseCase;
    this.getRecipeDetailsFromFormUseCase = getRecipeDetailsFromFormUseCase;
    this.deleteRecipeUseCase = deleteRecipeUseCase;
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
    Recipe recipe = getRecipeDetailsFromUrlUseCase.execute(recipeUrlForm.getUrl());
    addRecipeUseCase.execute(recipe);

    return "redirect:/recipes";
  }

  @PostMapping("/recipes/importFromForm")
  public String addRecipe(@Valid RecipeManualForm recipeManualForm) {
    Recipe recipe = getRecipeDetailsFromFormUseCase.execute(
        recipeManualForm.getName(),
        recipeManualForm.getUrl(),
        recipeManualForm.getIngredients(),
        recipeManualForm.getSteps(),
        recipeManualForm.getImgUrl());
    addRecipeUseCase.execute(recipe);

    return "redirect:/recipes";
  }

  @DeleteMapping("/recipes/{id}")
  public String deleteRecipe(@PathVariable Integer id) {
    deleteRecipeUseCase.execute(id);
    return "redirect:/recipes";
  }
}