package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
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