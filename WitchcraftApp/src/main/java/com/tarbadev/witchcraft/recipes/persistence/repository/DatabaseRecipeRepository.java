package com.tarbadev.witchcraft.recipes.persistence.repository;

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity;
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity;
import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseRecipeRepository implements RecipeRepository {
  private final RecipeEntityRepository recipeEntityRepository;

  public DatabaseRecipeRepository(RecipeEntityRepository recipeEntityRepository) {
    this.recipeEntityRepository = recipeEntityRepository;
  }

  @Override
  public Recipe saveRecipe(Recipe recipe) {
    return recipeEntityRepository.saveAndFlush(new RecipeEntity(recipe)).recipe();
  }

  @Override
  public Recipe updateRecipe(Recipe recipe) {
    RecipeEntity entity = recipeEntityRepository.findById(recipe.getId()).orElse(null);
    entity.setName(recipe.getName());
    entity.setOriginUrl(recipe.getOriginUrl());
    entity.setImgUrl(recipe.getImgUrl());
    entity.setIngredients(recipe.getIngredients().stream().map(IngredientEntity::new).collect(Collectors.toList()));
    entity.setSteps(recipe.getSteps().stream().map(StepEntity::new).collect(Collectors.toList()));
    return recipeEntityRepository.saveAndFlush(entity).recipe();
  }

  @Override
  public List<Recipe> findAll() {
    return recipeEntityRepository.findAllByOrderByName().stream()
        .map(RecipeEntity::recipe)
        .collect(Collectors.toList());
  }

  @Override
  public Recipe findById(Integer id) {
    return recipeEntityRepository.findById(id)
        .map(RecipeEntity::recipe)
        .map(recipe -> {
          recipe.getIngredients().sort(Comparator.comparing(Ingredient::getName));
          return recipe;
        })
        .orElse(null);
  }

  @Override
  public void delete(int id) {
    recipeEntityRepository.deleteById(id);
    recipeEntityRepository.flush();
  }

  @Override
  public void setFavorite(int id, Boolean favorite) {
    RecipeEntity recipeEntity = recipeEntityRepository.findById(id).get();
    recipeEntity.setFavorite(favorite);
    recipeEntityRepository.flush();
  }

  @Override
  public List<Recipe> findAllFavorite() {
    return recipeEntityRepository.findAllByFavorite(true).stream()
        .map(RecipeEntity::recipe)
        .collect(Collectors.toList());
  }

  @Override
  public List<Recipe> findLastAddedRecipes() {
    return recipeEntityRepository.findTop8ByOrderByIdDesc().stream()
        .map(RecipeEntity::recipe)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean existsById(Integer id) {
    return recipeEntityRepository.existsById(id);
  }
}
