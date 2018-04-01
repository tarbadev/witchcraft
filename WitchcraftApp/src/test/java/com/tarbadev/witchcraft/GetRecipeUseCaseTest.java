package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeUseCaseTest {
    private GetRecipeUseCase subject;

    @Autowired private TestResources testResources;
    @Autowired private DatabaseRecipeRepository databaseRecipeRepository;

    @Before
    public void setUp() {
        subject = new GetRecipeUseCase(databaseRecipeRepository);
    }

    @Test
    public void test_execute_returnsRecipe() {
        Recipe recipe = testResources.getRecipe();

        Integer recipeId = 123;
        given(databaseRecipeRepository.findById(recipeId)).willReturn(recipe);

        assertThat(subject.execute(recipeId)).isEqualTo(recipe);
    }
}