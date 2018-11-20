package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RecipeCatalogUseCaseTest {

    @Mock private RecipeRepository recipeRepository;

    private RecipeCatalogUseCase subject;

    @Before
    public void setUp() {
        subject = new RecipeCatalogUseCase(recipeRepository);
    }

    @Test
    public void execute() {
        List<Recipe> recipes = Arrays.asList(
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build()
        );

        given(recipeRepository.findAll()).willReturn(recipes);

        assertThat(subject.execute()).isEqualTo(recipes);
    }
}