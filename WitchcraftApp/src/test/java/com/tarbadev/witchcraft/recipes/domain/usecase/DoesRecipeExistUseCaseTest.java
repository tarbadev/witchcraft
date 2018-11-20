package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.recipes.domain.usecase.DoesRecipeExistUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DoesRecipeExistUseCaseTest {
  @Mock
  private RecipeRepository recipeRepository;
  private DoesRecipeExistUseCase subject;

  @Before
  public void setUp() {
    subject = new DoesRecipeExistUseCase(recipeRepository);
  }

  @Test
  public void execute() {
    int id = 32;

    given(recipeRepository.existsById(id)).willReturn(true);

    assertThat(subject.execute(id)).isTrue();
  }
}