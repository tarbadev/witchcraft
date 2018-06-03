package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LastAddedRecipesUseCaseTest {
  @Mock private RecipeRepository recipeRepository;

  private LastAddedRecipesUseCase subject;

  @Before
  public void setUp() {
    subject = new LastAddedRecipesUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );

    given(recipeRepository.findLastAddedRecipes()).willReturn(recipes);

    assertThat(subject.execute()).isEqualTo(recipes);
  }
}