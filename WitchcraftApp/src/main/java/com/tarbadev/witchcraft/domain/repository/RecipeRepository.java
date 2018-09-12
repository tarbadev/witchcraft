package com.tarbadev.witchcraft.domain.repository;

import com.tarbadev.witchcraft.domain.entity.Recipe;

import java.util.List;

public interface RecipeRepository {
  Recipe saveRecipe(Recipe recipe);

  Recipe updateRecipe(Recipe recipe);

  List<Recipe> findAll();

  Recipe findById(Integer id);

  void delete(int id);

  void rateRecipe(int id, double rating);

  List<Recipe> findTopFiveRecipes();

  List<Recipe> findLastAddedRecipes();

  Boolean existsById(Integer id);
}
