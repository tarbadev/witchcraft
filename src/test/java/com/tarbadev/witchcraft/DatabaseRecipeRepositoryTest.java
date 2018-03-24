package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseRecipeRepositoryTest {
    @Autowired private TestEntityManager entityManager;
    @Autowired private RecipeRepository recipeRepository;

    private DatabaseRecipeRepository subject;

    @Before
    public void setUp() {
        subject = new DatabaseRecipeRepository(recipeRepository);
    }

    @Test
    public void test_createRecipe_ReturnsRecipe() {
        String recipe_url = "URL";

        Recipe recipe = Recipe.builder().id(1).url(recipe_url).build();

        assertThat(subject.createRecipe(recipe_url)).isEqualTo(recipe);
    }

    @Test
    public void test_getAll_ReturnsAllRecipes() {
        String url1 = "URL1";
        String url2 = "URL2";

        List<Recipe> recipes = Arrays.asList(
                entityManager.persist(Recipe.builder().url(url1).build()),
                entityManager.persist(Recipe.builder().url(url2).build())
        );

        entityManager.flush();
        entityManager.clear();

        assertThat(subject.getAll()).isEqualTo(recipes);
    }
}