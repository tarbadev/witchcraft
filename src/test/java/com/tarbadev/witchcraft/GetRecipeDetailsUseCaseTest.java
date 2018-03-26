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
        Recipe recipe = testResources.getRecipe();

        assertThat(subject.execute(recipe.getUrl())).isEqualTo(recipe);
    }

    @Test
    public void test_execute_getsRecipeIngredients() {
        Recipe recipe = testResources.getRecipe();

        assertThat(subject.execute(recipe.getUrl())).isEqualTo(recipe);
        assertThat(recipe.getIngredients().size()).isEqualTo(8);
    }

    @Test
    public void test_execute_getsRecipeImageUrl() {
        Recipe recipe = testResources.getRecipe();

        assertThat(subject.execute(recipe.getUrl())).isEqualTo(recipe);
        assertThat(recipe.getImgUrl()).isNotEmpty();
    }
}