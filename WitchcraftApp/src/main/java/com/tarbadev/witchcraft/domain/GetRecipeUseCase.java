package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class GetRecipeUseCase {
    private RecipeRepository recipeRepository;

    public GetRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe execute(Integer recipeId) {
        return recipeRepository.findById(recipeId);
    }
}
