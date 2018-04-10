package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.TestResources;
import com.tarbadev.witchcraft.domain.GetRecipeDetailsUseCase;
import com.tarbadev.witchcraft.domain.Recipe;
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
public class GetRecipeDetailsUseCaseTest {
  private GetRecipeDetailsUseCase subject;

  @Autowired private TestResources testResources;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsUseCase();
  }

  @Test
  public void execute() {
    Recipe recipe = testResources.getRecipe();

    assertThat(subject.execute(recipe.getUrl())).isEqualTo(recipe);
  }

  @Test
  public void execute_getsRecipeName() {
    Recipe recipe = testResources.getRecipe();

    assertThat(subject.execute(recipe.getUrl())).isEqualTo(recipe);
  }

  @Test
  public void execute_getsRecipeIngredients() {
    Recipe recipe = testResources.getRecipe();

    assertThat(recipe.getIngredients().size()).isEqualTo(8);
  }

  @Test
  public void execute_getsRecipeImageUrl() {
    Recipe recipe = testResources.getRecipe();

    assertThat(recipe.getImgUrl()).isNotEmpty();
  }

  @Test
  public void execute_getsRecipesSteps() {
    Recipe recipe = testResources.getRecipe();

    assertThat(recipe.getSteps().size()).isEqualTo(6);
    assertThat(recipe.getSteps()).isEqualTo(recipe.getSteps());
  }
}