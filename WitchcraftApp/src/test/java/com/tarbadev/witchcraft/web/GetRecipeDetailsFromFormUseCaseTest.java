package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.IngredientFromStringUseCase;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeDetailsFromFormUseCaseTest {
  @Autowired private IngredientFromStringUseCase ingredientFromStringUseCase;

  private GetRecipeDetailsFromFormUseCase subject;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsFromFormUseCase(ingredientFromStringUseCase);
  }

  @Test
  public void execute() {
    String name = "Some recipe name";
    String url = "http://some/url/of/recipe";
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
        .url(url)
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

    assertThat(subject.execute(name, url, ingredients, steps)).isEqualTo(recipe);
  }
}