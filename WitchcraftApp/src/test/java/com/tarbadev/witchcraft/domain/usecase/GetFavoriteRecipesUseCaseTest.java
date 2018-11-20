package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.domain.usecase.GetFavoriteRecipesUseCase;
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
public class GetFavoriteRecipesUseCaseTest {
    @Mock private RecipeRepository recipeRepository;
    private GetFavoriteRecipesUseCase subject;

    @Before
    public void setUp() {
        subject = new GetFavoriteRecipesUseCase(recipeRepository);
    }

    @Test
    public void execute() {
        List<Recipe> recipes = Arrays.asList(
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build()
        );

        given(recipeRepository.findAllFavorite()).willReturn(recipes);

        assertThat(subject.execute()).isEqualTo(recipes);
    }
}