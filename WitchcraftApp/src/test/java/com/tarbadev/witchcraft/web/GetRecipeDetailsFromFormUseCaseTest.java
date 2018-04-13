package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import com.tarbadev.witchcraft.domain.RecipeRepository;
import com.tarbadev.witchcraft.domain.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetRecipeDetailsFromFormUseCaseTest {
  @Mock private RecipeRepository recipeRepository;

  private GetRecipeDetailsFromFormUseCase subject;

  @Before
  public void setUp() {
    subject = new GetRecipeDetailsFromFormUseCase(recipeRepository);
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

    given(recipeRepository.createRecipe(recipe)).willReturn(recipe);

    assertThat(subject.execute(name, url, ingredients, steps)).isEqualTo(recipe);
  }
}