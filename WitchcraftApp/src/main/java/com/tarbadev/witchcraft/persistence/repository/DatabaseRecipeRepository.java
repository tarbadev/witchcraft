package com.tarbadev.witchcraft.persistence.repository;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.persistence.helpers.DomainToEntity;
import com.tarbadev.witchcraft.persistence.helpers.EntityToDomain;
import com.tarbadev.witchcraft.persistence.entity.RecipeEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tarbadev.witchcraft.persistence.helpers.EntityToDomain.recipeMapper;

@Component
public class DatabaseRecipeRepository implements RecipeRepository {
  private final RecipeEntityRepository recipeEntityRepository;

  public DatabaseRecipeRepository(RecipeEntityRepository recipeEntityRepository) {
    this.recipeEntityRepository = recipeEntityRepository;
  }

  @Override
  public Recipe saveRecipe(Recipe recipe) {
    return recipeMapper(recipeEntityRepository.saveAndFlush(DomainToEntity.recipeEntityMapper(recipe)));
  }

  @Override
  public Recipe updateRecipe(Recipe recipe) {
    RecipeEntity entity = recipeEntityRepository.findById(recipe.getId()).orElse(null);
    entity.setName(recipe.getName());
    entity.setOriginUrl(recipe.getOriginUrl());
    entity.setImgUrl(recipe.getImgUrl());
    entity.setIngredients(recipe.getIngredients().stream().map(DomainToEntity::ingredientEntityMapper).collect(Collectors.toList()));
    entity.setSteps(recipe.getSteps().stream().map(DomainToEntity::stepEntityMapper).collect(Collectors.toList()));
    return recipeMapper(recipeEntityRepository.saveAndFlush(entity));
  }

  @Override
  public List<Recipe> findAll() {
    return recipeEntityRepository.findAllByOrderByName().stream()
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }

  @Override
  public Recipe findById(Integer id) {
    return recipeEntityRepository.findById(id)
        .map(EntityToDomain::recipeMapper)
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
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }

  @Override
  public List<Recipe> findLastAddedRecipes() {
    return recipeEntityRepository.findTop8ByOrderByIdDesc().stream()
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean existsById(Integer id) {
    return recipeEntityRepository.existsById(id);
  }
}
