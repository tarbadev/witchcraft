package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeleteRecipeUseCaseTest {
  @Mock private RecipeRepository recipeRepository;

  private DeleteRecipeUseCase subject;

  @Before
  public void setUp() {
    subject = new DeleteRecipeUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    subject.execute(123);

    verify(recipeRepository).delete(123);
  }
}