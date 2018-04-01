package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Autowired private DatabaseRecipeRepository databaseRecipeRepository;

    private RecipeCatalogUseCase subject;

    @Before
    public void setUp() {
        subject = new RecipeCatalogUseCase(databaseRecipeRepository);
    }

    @Test
    public void test_execute_ReturnsAllRecipes() {
        List<Recipe> recipes = Arrays.asList(
                Recipe.builder().build(),
                Recipe.builder().build(),
                Recipe.builder().build()
        );

        given(databaseRecipeRepository.findAll()).willReturn(recipes);

        assertThat(subject.execute()).isEqualTo(recipes);
    }
}