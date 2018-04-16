package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.TestResources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeDetailsFromUrlUseCaseTest {
  private GetRecipeDetailsFromUrlUseCase subject;

  @Autowired private TestResources testResources;
  @Autowired private IngredientFromStringUseCase ingredientFromStringUseCase;
  @Autowired private ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsFromUrlUseCase(ingredientFromStringUseCase, convertAndAddSameIngredientUseCase);
    Mockito.reset(convertAndAddSameIngredientUseCase);
  }

  @Test
  public void execute() {
    Recipe recipe = testResources.getRecipe();

    given(convertAndAddSameIngredientUseCase.execute(
        Arrays.asList(
            Ingredient.builder()
                .name("Little Potato Co. Creamer potatoes (I used Dynamic Duo)")
                .quantity(1.5)
                .unit("lb")
                .build(),
            Ingredient.builder()
                .name("soft goat cheese (chevre), room temperature")
                .quantity(2.0)
                .unit("oz")
                .build(),
            Ingredient.builder()
                .name("diced roasted red pepper")
                .quantity(3.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("pitted Kalamata olives, diced")
                .quantity(4.0)
                .unit("")
                .build(),
            Ingredient.builder()
                .name("minced flat-leaf parsley")
                .quantity(1.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("soft goat cheese (chevre), room temperature")
                .quantity(2.0)
                .unit("oz")
                .build(),
            Ingredient.builder()
                .name("pistachios halves, divided")
                .quantity(3.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("honey")
                .quantity(1.0)
                .unit("tsp")
                .build(),
            Ingredient.builder()
                .name("ground cinnamon")
                .quantity(0.25)
                .unit("tsp")
                .build()))
    ).willReturn(recipe.getIngredients());

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe).isEqualTo(recipe);
  }

  @Test
  public void execute_getsRecipeName() {
    Recipe recipe = testResources.getRecipe();

    given(convertAndAddSameIngredientUseCase.execute(recipe.getIngredients()))
        .willReturn(recipe.getIngredients());

    assertThat(subject.execute(recipe.getUrl()).getName()).isEqualTo(recipe.getName());
  }

  @Test
  public void execute_getsRecipeIngredients() {
    Recipe recipe = testResources.getRecipe();

    given(convertAndAddSameIngredientUseCase.execute(
        Arrays.asList(
            Ingredient.builder()
                .name("Little Potato Co. Creamer potatoes (I used Dynamic Duo)")
                .quantity(1.5)
                .unit("lb")
                .build(),
            Ingredient.builder()
                .name("soft goat cheese (chevre), room temperature")
                .quantity(2.0)
                .unit("oz")
                .build(),
            Ingredient.builder()
                .name("diced roasted red pepper")
                .quantity(3.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("pitted Kalamata olives, diced")
                .quantity(4.0)
                .unit("")
                .build(),
            Ingredient.builder()
                .name("minced flat-leaf parsley")
                .quantity(1.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("soft goat cheese (chevre), room temperature")
                .quantity(2.0)
                .unit("oz")
                .build(),
            Ingredient.builder()
                .name("pistachios halves, divided")
                .quantity(3.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("honey")
                .quantity(1.0)
                .unit("tsp")
                .build(),
            Ingredient.builder()
                .name("ground cinnamon")
                .quantity(0.25)
                .unit("tsp")
                .build()))
    ).willReturn(recipe.getIngredients());

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe.getIngredients()).isEqualTo(recipe.getIngredients());
  }

  @Test
  public void execute_getsRecipeImageUrl() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());
    assertThat(returnedRecipe.getImgUrl()).isEqualTo(recipe.getImgUrl());
  }

  @Test
  public void execute_getsRecipesSteps() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getUrl());

    assertThat(returnedRecipe.getSteps().size()).isEqualTo(6);
    assertThat(returnedRecipe.getSteps()).isEqualTo(recipe.getSteps());
  }
}