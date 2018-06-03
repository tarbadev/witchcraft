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
public class BestRatedRecipesUseCaseTest {
    @Mock private RecipeRepository recipeRepository;
    private BestRatedRecipesUseCase subject;

    @Before
    public void setUp() {
        subject = new BestRatedRecipesUseCase(recipeRepository);
    }

    @Test
    public void execute() {
        List<Recipe> recipes = Arrays.asList(
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build()
        );

        given(recipeRepository.findTopFiveRecipes()).willReturn(recipes);

        assertThat(subject.execute()).isEqualTo(recipes);
    }
}