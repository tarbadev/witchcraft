package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SetFavoriteRecipeUseCaseTest {
  @Mock
  private RecipeRepository recipeRepository;

  private SetFavoriteRecipeUseCase subject;

  @Before
  public void setUp() {
    subject = new SetFavoriteRecipeUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    subject.execute(123, true);

    verify(recipeRepository).setFavorite(123, true);
  }
}