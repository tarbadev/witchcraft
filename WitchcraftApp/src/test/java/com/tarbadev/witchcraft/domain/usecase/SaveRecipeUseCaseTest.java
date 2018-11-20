package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SaveRecipeUseCaseTest {
  private SaveRecipeUseCase saveRecipeUseCase;

  @Mock private RecipeRepository recipeRepository;

  @Before
  public void setUp() {
    saveRecipeUseCase = new SaveRecipeUseCase(recipeRepository);
    reset(recipeRepository);
  }

  @Test
  public void execute() {
    Recipe recipe = Recipe.builder().build();

    saveRecipeUseCase.execute(recipe);

    verify(recipeRepository).saveRecipe(recipe);
  }

  @Test
  public void execute_updatesRecipe() {
    Recipe recipe = Recipe.builder().id(123).build();

    saveRecipeUseCase.execute(recipe);

    verify(recipeRepository).updateRecipe(recipe);
  }
}