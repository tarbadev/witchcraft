package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tarbadev.witchcraft.persistence.EntityToDomain.recipeMapper;

@Component
public class DatabaseRecipeRepository implements RecipeRepository {
  private RecipeEntityRepository recipeEntityRepository;

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
    entity.setUrl(recipe.getOriginUrl());
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
  public void rateRecipe(int id, double rating) {
    RecipeEntity recipeEntity = recipeEntityRepository.findById(id).get();
    recipeEntity.setRating(rating);
    recipeEntityRepository.flush();
  }

  @Override
  public List<Recipe> findTopFiveRecipes() {
    return recipeEntityRepository.findTop5ByOrderByRatingDesc().stream()
        .filter(recipeEntity -> recipeEntity.getRating() != null)
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }

  @Override
  public List<Recipe> findLastAddedRecipes() {
    return recipeEntityRepository.findTop5ByOrderByIdDesc().stream()
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }
}
