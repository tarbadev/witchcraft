package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.TestResources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SaveRecipeUseCaseTest {
  private SaveRecipeUseCase subject;

  @Autowired private TestResources testResources;
  @Mock private RecipeRepository recipeRepository;

  @Before
  public void setUp() {
    subject = new SaveRecipeUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    Recipe recipe = testResources.getRecipe();

    subject.execute(recipe);

    verify(recipeRepository).saveRecipe(recipe);
  }

  @Test
  public void execute_updatesRecipe() {
    Recipe recipe = Recipe.builder().id(123).build();

    subject.execute(recipe);

    verify(recipeRepository).updateRecipe(recipe);
  }
}