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
    @Autowired private DatabaseRecipeRepository databaseRecipeRepository;

    @Before
    public void setUp() {
        subject = new AddRecipeUseCase(databaseRecipeRepository);
    }

    @Test
    public void execute_returnsRecipe() {
        Recipe recipe = testResources.getRecipe();
        Recipe expectedRecipe = Recipe.builder().id(123).url(recipe.getUrl()).build();

        given(databaseRecipeRepository.createRecipe(recipe)).willReturn(expectedRecipe);

        assertThat(subject.execute(recipe)).isEqualTo(expectedRecipe);

        verify(databaseRecipeRepository).createRecipe(recipe);
    }
}