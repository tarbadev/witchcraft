package com.tarbadev.witchcraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RecipesController {
    private AddRecipeUseCase addRecipeUseCase;

    @Autowired
    public RecipesController(AddRecipeUseCase recipeService) {
        this.addRecipeUseCase = recipeService;
    }

    @GetMapping("/recipes/import")
    public String showImportForm(RecipeForm recipeForm) {
        return "recipes/index";
    }

    @PostMapping("/recipes/import")
    public String addRecipe(@Valid RecipeForm recipeForm) {
        addRecipeUseCase.execute(recipeForm.getUrl());

        return "recipes/index";
    }
}
