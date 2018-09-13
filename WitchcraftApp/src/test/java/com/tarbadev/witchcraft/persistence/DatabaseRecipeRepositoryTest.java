package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import com.tarbadev.witchcraft.persistence.entity.RecipeEntity;
import com.tarbadev.witchcraft.persistence.helpers.EntityToDomain;
import com.tarbadev.witchcraft.persistence.repository.DatabaseRecipeRepository;
import com.tarbadev.witchcraft.persistence.repository.IngredientEntity;
import com.tarbadev.witchcraft.persistence.repository.RecipeEntityRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
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
            .name("Lasagna")
            .originUrl(recipe_url)
            .ingredients(emptyList())
            .steps(emptyList())
            .build()
    );
    Recipe expectedRecipe = Recipe.builder()
        .id(recipe.getId())
        .name("lasagna")
        .originUrl(recipe_url)
        .ingredients(emptyList())
        .steps(emptyList())
        .build();

    assertThat(recipe).isEqualTo(expectedRecipe);
  }

  @Test
  public void saveRecipe_savesIngredients() {
    Recipe recipe = Recipe.builder()
        .name("Lasagna")
        .ingredients(Arrays.asList(
            Ingredient.builder().build(),
            Ingredient.builder().build()
        ))
        .steps(emptyList())
        .build();

    Recipe returnedRecipe = subject.saveRecipe(recipe);

    Recipe expectedRecipe = Recipe.builder()
        .id(returnedRecipe.getId())
        .name("lasagna")
        .ingredients(Arrays.asList(
            Ingredient.builder()
                .id(returnedRecipe.getIngredients().get(0).getId())
                .build(),
            Ingredient.builder()
                .id(returnedRecipe.getIngredients().get(1).getId())
                .build()
        ))
        .steps(emptyList())
        .build();

    assertThat(returnedRecipe).isEqualTo(expectedRecipe);
  }

  @Test
  public void updateRecipe_updatesRecipe() {
    Recipe recipe = subject.saveRecipe(
        Recipe.builder()
            .name("Name uncorrect")
            .originUrl("URL")
            .ingredients(emptyList())
            .steps(emptyList())
            .build()
    );

    entityManager.clear();

    Recipe modifiedRecipe = Recipe.builder()
        .id(recipe.getId())
        .name("fixed name")
        .originUrl(recipe.getOriginUrl())
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
            .name("Lasagna")
            .ingredients(emptyList())
            .originUrl(url1)
            .imgUrl("imgUrl1")
            .build()),
        entityManager.persist(RecipeEntity.builder()
            .name("Tartiflette")
            .ingredients(emptyList())
            .originUrl(url2)
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
            .ingredients(emptyList())
            .steps(emptyList())
            .build())
    );
    Recipe pizza = toDomain(
        entityManager.persist(RecipeEntity.builder()
            .name("Pizza")
            .ingredients(emptyList())
            .steps(emptyList())
            .build())
    );
    Recipe burger = toDomain(
        entityManager.persistAndFlush(RecipeEntity.builder()
            .name("Burger")
            .ingredients(emptyList())
            .steps(emptyList())
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
            .ingredients(emptyList())
            .steps(emptyList())
            .originUrl("URL")
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
            .steps(emptyList())
            .originUrl("URL")
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
        .name(recipeEntity.getName().toLowerCase())
        .originUrl(recipeEntity.getOriginUrl())
        .imgUrl(recipeEntity.getImgUrl())
        .ingredients(recipeEntity.getIngredients().stream()
            .map(ingredientEntity -> Ingredient.builder()
                .id(ingredientEntity.getId())
                .name(ingredientEntity.getName())
                .quantity(ingredientEntity.getQuantity())
                .unit(ingredientEntity.getUnit())
                .build())
            .collect(toList()))
        .steps(recipeEntity.getSteps().stream()
            .map(stepEntity -> Step.builder()
                .id(stepEntity.getId())
                .name(stepEntity.getName())
                .build())
            .collect(toList()))
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
  public void setFavorite() {
    RecipeEntity recipe = entityManager.persistAndFlush(RecipeEntity.builder().name("Lasagna").build());
    entityManager.clear();

    assertThat(subject.findById(recipe.getId()).getFavorite()).isFalse();

    subject.setFavorite(recipe.getId(), true);
    entityManager.clear();

    assertThat(subject.findById(recipe.getId()).getFavorite()).isTrue();
  }

  @Test
  public void findAllFavorite() {
    List<Recipe> recipes = Stream.of(
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(true).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(true).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(false).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(true).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(true).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(false).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(true).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).favorite(false).build())
    )
        .filter(RecipeEntity::getFavorite)
        .map(EntityToDomain::recipeMapper)
        .collect(toList());

    entityManager.flush();
    entityManager.clear();

    assertThat(subject.findAllFavorite()).isEqualTo(recipes);
  }

  @Test
  public void findLastAddedRecipes() {
    List<Recipe> recipes = Stream.of(
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build()),
        entityManager.persist(RecipeEntity.builder().name("").ingredients(emptyList()).steps(emptyList()).build())
    )
        .map(EntityToDomain::recipeMapper)
        .sorted(Comparator.comparing(Recipe::getId).reversed())
        .limit(5)
        .collect(toList());

    entityManager.flush();
    entityManager.clear();

    assertThat(subject.findLastAddedRecipes()).isEqualTo(recipes);
  }

  @Test
  public void exists_returnsTrueWhenRecipeExists() {
    RecipeEntity recipeEntity = entityManager.persistAndFlush(
        RecipeEntity.builder().build()
    );

    assertThat(subject.existsById(recipeEntity.getId())).isTrue();
  }

  @Test
  public void exists_returnsFalseWhenRecipeDoesNotExist() {
    assertThat(subject.existsById(32)).isFalse();
  }
}