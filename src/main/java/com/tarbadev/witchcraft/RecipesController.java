package com.tarbadev.witchcraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipesController {
    private RecipesService recipeService;

    @Autowired
    public RecipesController(RecipesService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/import")
    public ModelAndView showImportForm() {
        return new ModelAndView("recipes/index", "recipe", new Recipe());
    }

    @PostMapping("/recipes/import")
    public String addRecipe(@ModelAttribute Recipe recipe) {
        System.out.println("recipe = " + recipe);
        recipeService.addRecipe(recipe);

        return "recipes/index";
    }
}
