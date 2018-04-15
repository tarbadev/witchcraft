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
  public Recipe createRecipe(Recipe recipe) {
    RecipeEntity recipeEntity = RecipeEntity.builder()
        .name(recipe.getName())
        .url(recipe.getUrl())
        .imgUrl(recipe.getImgUrl())
        .ingredients(recipe.getIngredients().stream().map(DomainToEntity::ingredientEntityMapper).collect(Collectors.toList()))
        .steps(recipe.getSteps().stream().map(DomainToEntity::stepEntityMapper).collect(Collectors.toList()))
        .build();
    return recipeMapper(recipeEntityRepository.saveAndFlush(recipeEntity));
  }

  @Override
  public List<Recipe> findAll() {
    return recipeEntityRepository.findAllByOrderByName().stream()
        .map(EntityToDomain::recipeMapper)
        .collect(Collectors.toList());
  }

  @Override
  public Recipe findById(Integer recipeId) {
    Recipe recipe = recipeMapper(recipeEntityRepository.findById(recipeId).get());
    recipe.getIngredients().sort(Comparator.comparing(Ingredient::getName));
    return recipe;
  }
}
