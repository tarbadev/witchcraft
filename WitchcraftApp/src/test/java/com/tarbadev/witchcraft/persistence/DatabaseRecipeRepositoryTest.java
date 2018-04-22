package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.Step;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class DatabaseRecipeRepositoryTest {
  @Autowired private TestEntityManager entityManager;
  @Autowired private RecipeEntityRepository recipeEntityRepository;

  private DatabaseRecipeRepository subject;

  @Before
  public void setUp() {
    subject = new DatabaseRecipeRepository(recipeEntityRepository);
  }

  @Test
  public void saveRecipe() {
    String recipe_url = "URL";

    Recipe recipe = subject.saveRecipe(
        Recipe.builder()
            .url(recipe_url)
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .build()
    );
    Recipe expectedRecipe = Recipe.builder()
        .id(recipe.getId())
        .url(recipe_url)
        .ingredients(Collections.emptyList())
        .steps(Collections.emptyList())
        .build();

    assertThat(recipe).isEqualTo(expectedRecipe);
  }

  @Test
  public void saveRecipe_savesIngredients() {
    Recipe recipe = Recipe.builder()
        .ingredients(Arrays.asList(
            Ingredient.builder().build(),
            Ingredient.builder().build()
        ))
        .steps(Collections.emptyList())
        .build();

    Recipe returnedRecipe = subject.saveRecipe(recipe);

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
        .steps(Collections.emptyList())
        .build();

    assertThat(returnedRecipe).isEqualTo(expectedRecipe);
  }

  @Test
  public void updateRecipe_updatesRecipe() {
    Recipe recipe = subject.saveRecipe(
        Recipe.builder()
            .name("Name uncorrect")
            .url("URL")
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .build()
    );

    entityManager.clear();

    Recipe modifiedRecipe = Recipe.builder()
        .id(recipe.getId())
        .name("Fixed name")
        .url(recipe.getUrl())
        .imgUrl(recipe.getImgUrl())
        .ingredients(recipe.getIngredients())
        .steps(recipe.getSteps())
        .build();

    recipe = subject.updateRecipe(modifiedRecipe);

    assertThat(recipe.getName()).isEqualTo(modifiedRecipe.getName());
  }

  @Test
  public void findAll() {
    String url1 = "URL1";
    String url2 = "URL2";


    List<RecipeEntity> expectedRecipes = Arrays.asList(
        entityManager.persist(RecipeEntity.builder()
            .ingredients(Collections.emptyList())
            .url(url1)
            .imgUrl("imgUrl1")
            .build()),
        entityManager.persist(RecipeEntity.builder()
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
  public void findAll_returnsRecipeOrderedByName() {
    Recipe tartiflette = toDomain(
        entityManager.persist(RecipeEntity.builder()
            .name("Tartiflette")
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .build())
    );
    Recipe pizza = toDomain(
        entityManager.persist(RecipeEntity.builder()
            .name("Pizza")
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .build())
    );
    Recipe burger = toDomain(
        entityManager.persistAndFlush(RecipeEntity.builder()
            .name("Burger")
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .build())
    );

    entityManager.clear();

    List<Recipe> expectedRecipes = Arrays.asList(burger, pizza, tartiflette);

    List<Recipe> recipes = subject.findAll();
    assertThat(recipes.size()).isEqualTo(expectedRecipes.size());
    assertThat(recipes).isSortedAccordingTo(Comparator.comparing(Recipe::getName));
    assertThat(recipes.get(0).getName()).isEqualTo(burger.getName());
    assertThat(recipes.get(2).getName()).isEqualTo(tartiflette.getName());
  }

  @Test
  public void findById() {
    Recipe recipe = toDomain(
        entityManager.persistAndFlush(RecipeEntity.builder()
            .name("Recipe 1")
            .ingredients(Collections.emptyList())
            .steps(Collections.emptyList())
            .url("URL")
            .build()
        )
    );

    entityManager.clear();

    assertThat(subject.findById(recipe.getId())).isEqualToComparingFieldByFieldRecursively(recipe);
  }

  @Test
  public void findById_returnsIngredientsOrderedByName() {
    Recipe recipe = toDomain(
        entityManager.persistAndFlush(RecipeEntity.builder()
            .name("Recipe 1")
            .ingredients(Arrays.asList(
                IngredientEntity.builder().name("Parsley").build(),
                IngredientEntity.builder().name("Cilantro").build(),
                IngredientEntity.builder().name("Egg").build()
            ))
            .steps(Collections.emptyList())
            .url("URL")
            .build()
        )
    );

    recipe.getIngredients().sort(Comparator.comparing(Ingredient::getName));

    entityManager.clear();

    assertThat(subject.findById(recipe.getId())).isEqualToComparingFieldByFieldRecursively(recipe);
  }

  private Recipe toDomain(RecipeEntity recipeEntity) {
    return Recipe.builder()
        .id(recipeEntity.getId())
        .name(recipeEntity.getName())
        .url(recipeEntity.getUrl())
        .imgUrl(recipeEntity.getImgUrl())
        .ingredients(recipeEntity.getIngredients().stream()
            .map(ingredientEntity -> Ingredient.builder()
                .id(ingredientEntity.getId())
                .name(ingredientEntity.getName())
                .quantity(ingredientEntity.getQuantity())
                .unit(ingredientEntity.getUnit())
                .build())
            .collect(Collectors.toList()))
        .steps(recipeEntity.getSteps().stream()
            .map(stepEntity -> Step.builder()
                .id(stepEntity.getId())
                .name(stepEntity.getName())
                .build())
            .collect(Collectors.toList()))
        .build();
  }

  @Test
  public void delete() {
    RecipeEntity recipeEntity = entityManager.persistAndFlush(RecipeEntity.builder().name("Lasagna").build());

    entityManager.clear();

    assertThat(subject.findById(recipeEntity.getId())).isNotNull();

    subject.delete(recipeEntity.getId());

    entityManager.clear();

    assertThat(subject.findById(recipeEntity.getId())).isNull();
  }

  @Test
  public void rateRecipe() {
    Double rating = 4.5;
    RecipeEntity recipe = entityManager.persistAndFlush(RecipeEntity.builder().build());
    entityManager.clear();

    assertThat(subject.findById(recipe.getId()).getRating()).isNull();

    subject.rateRecipe(recipe.getId(), rating);
    entityManager.clear();

    assertThat(subject.findById(recipe.getId()).getRating()).isEqualTo(rating);
  }
}