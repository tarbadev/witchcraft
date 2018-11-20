package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Step;
import com.tarbadev.witchcraft.domain.usecase.ConvertAndAddSameIngredientUseCase;
import com.tarbadev.witchcraft.domain.usecase.GetRecipeDetailsFromFormUseCase;
import com.tarbadev.witchcraft.domain.usecase.IngredientFromStringUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeDetailsFromFormUseCaseTest {
  @Autowired private IngredientFromStringUseCase ingredientFromStringUseCase;
  @Autowired private ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientsUseCase;

  private GetRecipeDetailsFromFormUseCase subject;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsFromFormUseCase(ingredientFromStringUseCase, convertAndAddSameIngredientsUseCase);
    Mockito.reset(convertAndAddSameIngredientsUseCase);
  }

  @Test
  public void execute() {
    String name = "Some recipe name";
    String originUrl = "http://some/originUrl/of/recipe";
    String imgUrl = "http://some/originUrl/of/recipe.png";
    String ingredients = String.join("\n"
        , "10 tbsp sugar"
        , "1/2 cup olive oil"
        , "1 lemon"
    );
    String steps = String.join("\n"
        , "Add ingredients and stir"
        , "Serve on each plate"
    );

    Recipe recipe = Recipe.builder()
        .name(name)
        .originUrl(originUrl)
        .imgUrl(imgUrl)
        .ingredients(Arrays.asList(
            Ingredient.builder()
                .name("sugar")
                .quantity(10.0)
                .unit("tbsp")
                .build(),
            Ingredient.builder()
                .name("olive oil")
                .quantity(0.5)
                .unit("cup")
                .build(),
            Ingredient.builder()
                .name("lemon")
                .quantity(1.0)
                .unit("")
                .build()
        ))
        .steps(Arrays.stream(steps.split("\n"))
            .map(step -> Step.builder().name(step).build())
            .collect(Collectors.toList()))
        .build();

    given(convertAndAddSameIngredientsUseCase.execute(recipe.getIngredients()))
        .willReturn(recipe.getIngredients());

    assertThat(subject.execute(name, originUrl, ingredients, steps, imgUrl)).isEqualTo(recipe);
  }

  @Test
  public void execute_addsSameIngredients() {
    String name = "Some recipe name";
    String url = "http://some/originUrl/of/recipe";
    String imgUrl = "http://some/originUrl/of/recipe.png";
    String ingredients = String.join("\n"
        , "10 tbsp sugar"
        , "1/2 cup olive oil"
        , "3 tsp olive oil"
        , "1 lemon"
    );
    String steps = String.join("\n"
        , "Add ingredients and stir"
        , "Serve on each plate"
    );

    List<Ingredient> allIngredients = Arrays.asList(
        Ingredient.builder()
            .name("sugar")
            .quantity(10.0)
            .unit("tbsp")
            .build(),
        Ingredient.builder()
            .name("olive oil")
            .quantity(0.5)
            .unit("cup")
            .build(),
        Ingredient.builder()
            .name("olive oil")
            .quantity(3.0)
            .unit("tsp")
            .build(),
        Ingredient.builder()
            .name("lemon")
            .quantity(1.0)
            .unit("")
            .build()
    );

    List<Ingredient> expectedIngredients = Arrays.asList(
        Ingredient.builder()
            .name("sugar")
            .quantity(10.0)
            .unit("tbsp")
            .build(),
        Ingredient.builder()
            .name("olive oil")
            .quantity(0.5625)
            .unit("cup")
            .build(),
        Ingredient.builder()
            .name("lemon")
            .quantity(1.0)
            .unit("")
            .build()
    );
    Recipe recipe = Recipe.builder()
        .name(name)
        .originUrl(url)
        .imgUrl(imgUrl)
        .ingredients(
            expectedIngredients)
        .steps(Arrays.stream(steps.split("\n"))
            .map(step -> Step.builder().name(step).build())
            .collect(Collectors.toList()))
        .build();

    given(convertAndAddSameIngredientsUseCase.execute(allIngredients)).willReturn(expectedIngredients);

    assertThat(subject.execute(name, url, ingredients, steps, imgUrl)).isEqualTo(recipe);
  }
}