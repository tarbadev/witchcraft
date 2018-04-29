package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RateRecipeUseCaseTest {
  @Mock private RecipeRepository recipeRepository;

  private RateRecipeUseCase subject;

  @Before
  public void setUp() {
    subject = new RateRecipeUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    subject.execute(123, 4.5);

    verify(recipeRepository).rateRecipe(123, 4.5);
  }
}