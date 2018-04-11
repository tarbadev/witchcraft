package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
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