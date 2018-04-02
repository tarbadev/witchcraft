package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
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

        Recipe recipe = Recipe.builder()
                .url(recipe_url)
                .ingredients(Collections.emptyList())
                .build();
        recipe = subject.createRecipe(recipe);
        Recipe expectedRecipe = Recipe.builder()
                .id(recipe.getId())
                .url(recipe_url)
                .ingredients(Collections.emptyList())
                .build();

        assertThat(recipe).isEqualToComparingFieldByFieldRecursively(expectedRecipe);
    }

    @Test
    public void test_createRecipe_savesIngredients() {
        Recipe recipe = Recipe.builder()
                .ingredients(Arrays.asList(
                        Ingredient.builder().build(),
                        Ingredient.builder().build()
                ))
                .build();

        Recipe returnedRecipe = subject.createRecipe(recipe);

        Recipe expectedRecipe = Recipe.builder()
                .id(returnedRecipe.getId())
                .ingredients(Arrays.asList(
                        Ingredient.builder()
                                .id(returnedRecipe.getIngredients().get(0).getId())
                                .build(),
                        Ingredient.builder()
                                .id(returnedRecipe.getIngredients().get(1).getId())
                                .build()
                ))
                .build();

        assertThat(returnedRecipe).isEqualToComparingFieldByFieldRecursively(expectedRecipe);
    }

    @Test
    public void test_findAll_ReturnsAllRecipes() {
        String url1 = "URL1";
        String url2 = "URL2";


        List<Recipe> expectedRecipes = Arrays.asList(
                entityManager.persist(Recipe.builder()
                        .ingredients(Collections.emptyList())
                        .url(url1)
                        .imgUrl("imgUrl1")
                        .build()),
                entityManager.persist(Recipe.builder()
                        .ingredients(Collections.emptyList())
                        .url(url2)
                        .imgUrl("imgUrl2")
                        .build())
        );

        entityManager.flush();
        entityManager.clear();

        assertThat(subject.findAll().size()).isEqualTo(expectedRecipes.size());
    }

    @Test
    public void test_findById_returnsRecipe() {
        Recipe recipe = entityManager.persistAndFlush(Recipe.builder()
                .name("Recipe 1")
                .ingredients(Collections.emptyList())
                .url("URL")
                .build()
        );

        entityManager.clear();

        assertThat(subject.findById(recipe.getId())).isEqualToComparingFieldByFieldRecursively(recipe);
    }
}