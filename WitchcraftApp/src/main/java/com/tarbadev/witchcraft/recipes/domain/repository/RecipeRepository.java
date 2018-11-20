package com.tarbadev.witchcraft.recipes.domain.repository;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;

import java.util.List;

public interface RecipeRepository {
  Recipe saveRecipe(Recipe recipe);

  Recipe updateRecipe(Recipe recipe);

  List<Recipe> findAll();

  Recipe findById(Integer id);

  void delete(int id);

  void setFavorite(int id, Boolean isFavorite);

  List<Recipe> findAllFavorite();

  List<Recipe> findLastAddedRecipes();

  Boolean existsById(Integer id);
}
