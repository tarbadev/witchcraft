package com.tarbadev.witchcraft;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipesController {
    private AddRecipeUseCase addRecipeUseCase;
    private RecipeCatalogUseCase recipeCatalogUseCase;
    private GetRecipeDetailsUseCase getRecipeDetailsUseCase;

    public RecipesController(AddRecipeUseCase recipeService, RecipeCatalogUseCase recipeCatalogUseCase, GetRecipeDetailsUseCase getRecipeDetailsUseCase) {
        this.addRecipeUseCase = recipeService;
        this.recipeCatalogUseCase = recipeCatalogUseCase;
        this.getRecipeDetailsUseCase = getRecipeDetailsUseCase;
    }

    @GetMapping("/recipes")
    public String show(RecipeForm recipeForm, Model model) {
        List<Recipe> recipes = recipeCatalogUseCase.execute();

        model.addAttribute("recipes", recipes);

        return "recipes/index";
    }

    @PostMapping("/recipes/import")
    public String addRecipe(@Valid RecipeForm recipeForm) {
        Recipe recipe = getRecipeDetailsUseCase.execute(recipeForm.getUrl());
        addRecipeUseCase.execute(recipe);

        return "redirect:/recipes";
    }
}
