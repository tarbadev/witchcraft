package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.TestResources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeDetailsFromUrlUseCaseTest {
  private GetRecipeDetailsFromUrlUseCase subject;

  @Autowired private TestResources testResources;
  @Autowired private IngredientFromStringUseCase ingredientFromStringUseCase;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsFromUrlUseCase(ingredientFromStringUseCase);
  }

  @Test
  public void execute() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe).isEqualTo(recipe);
  }

  @Test
  public void execute_getsRecipeName() {
    Recipe recipe = testResources.getRecipe();

    assertThat(subject.execute(recipe.getUrl()).getName()).isEqualTo(recipe.getName());
  }

  @Test
  public void execute_getsRecipeIngredients() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe.getIngredients().size()).isEqualTo(8);
    assertThat(returnedRecipe.getIngredients()).isEqualTo(recipe.getIngredients());
  }

  @Test
  public void execute_getsRecipeImageUrl() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe.getImgUrl()).isEqualTo(recipe.getImgUrl());
  }

  @Test
  public void execute_getsRecipesSteps() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());

    assertThat(returnedRecipe.getSteps().size()).isEqualTo(6);
    assertThat(returnedRecipe.getSteps()).isEqualTo(recipe.getSteps());
  }
}