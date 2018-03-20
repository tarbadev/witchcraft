package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddRecipeUseCaseTest {
    private AddRecipeUseCase subject;

    @Autowired private TestResources testResources;
    @Autowired private RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        subject = new AddRecipeUseCase(recipeRepository);
    }

    @Test
    public void execute_returnsRecipe() {
        String recipe_url = testResources.getRecipeUrl();
        Recipe recipe = Recipe.builder().url(recipe_url).build();

        given(recipeRepository.createRecipe(recipe_url)).willReturn(recipe);

        assertThat(subject.execute(recipe_url)).isEqualTo(recipe);

        verify(recipeRepository).createRecipe(recipe_url);
    }
}