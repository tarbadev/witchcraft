package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeDetailsUseCaseTest {
    private GetRecipeDetailsUseCase subject;

    @Autowired private TestResources testResources;

    @Before
    public void setUp() {
        subject = new GetRecipeDetailsUseCase();
    }

    @Test
    public void test_execute_getsRecipeName() {
        String recipeUrl = testResources.getRecipeUrl();
        String recipeName = testResources.getRecipeName();
        Recipe recipe = Recipe.builder().url(recipeUrl).name(recipeName).build();

        assertThat(subject.execute(recipeUrl)).isEqualTo(recipe);
    }
}