package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DatabaseRecipeRepositoryTest {

    @Autowired private TestResources testResources;
    @Autowired private RecipeRepository recipeRepository;

    private DatabaseRecipeRepository subject;

    @Before
    public void setUp() {
        subject = new DatabaseRecipeRepository(recipeRepository);
    }

    @Test
    public void test_createRecipe_ReturnsRecipe() {
        String recipe_url = testResources.getRecipeUrl();

        Recipe recipe = Recipe.builder().id(1).url(recipe_url).build();

        assertThat(subject.createRecipe(recipe_url)).isEqualTo(recipe);
    }
}