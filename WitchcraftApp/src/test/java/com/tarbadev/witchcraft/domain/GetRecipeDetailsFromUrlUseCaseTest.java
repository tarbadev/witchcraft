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
import java.util.List;

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

    Recipe returnedRecipe = subject.execute(recipe.getOriginUrl());
    assertThat(returnedRecipe).isEqualTo(recipe);
  }

  @Test
  public void execute_getsRecipeName() {
    Recipe recipe = testResources.getRecipe();

    given(convertAndAddSameIngredientUseCase.execute(recipe.getIngredients()))
        .willReturn(recipe.getIngredients());

    assertThat(subject.execute(recipe.getOriginUrl()).getName()).isEqualTo(recipe.getName());
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

    Recipe returnedRecipe = subject.execute(recipe.getOriginUrl());
    assertThat(returnedRecipe.getIngredients()).isEqualTo(recipe.getIngredients());
  }

  @Test
  public void execute_getsRecipeImageUrl() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getOriginUrl());
    assertThat(returnedRecipe.getImgUrl()).isEqualTo(recipe.getImgUrl());
  }

  @Test
  public void execute_getsRecipesSteps() {
    Recipe recipe = testResources.getRecipe();

    Recipe returnedRecipe = subject.execute(recipe.getOriginUrl());

    assertThat(returnedRecipe.getSteps().size()).isEqualTo(6);
    assertThat(returnedRecipe.getSteps()).isEqualTo(recipe.getSteps());
  }

  @Test
  public void execute_ImportStepsFromParagraphTag() {
    Recipe recipe = subject.execute("https://www.cookincanuck.com/one-pot-whole-wheat-pasta-recipe-chicken-spinach/");

    List<Step> steps = Arrays.asList(
        Step.builder().name("In a large nonstick saucepan (or nonstick skillet with high sides) set over medium-high heat, heat 2 teaspoons of olive oil. Add the chicken and cook, stirring occasionally, until just cooked through, 4 to 5 minutes. Transfer to a bowl.").build(),
        Step.builder().name("Reduce the heat to medium and add 1 teaspoon of olive oil to the saucepan. Add the orange pepper and cook for 1 minute. Transfer to the bowl with the chicken. Set aside.").build(),
        Step.builder().name("Add the remaining 1 teaspoon of olive oil to the saucepan. Add the onion and sauté until softened, about 4 minutes. Add the garlic and oregano, and cook for 30 seconds.").build(),
        Step.builder().name("Pour in the diced tomatoes, chicken broth and balsamic vinegar. Bring to a boil, then stir in the pasta, stirring to coat and submerge the pasta.").build(),
        Step.builder().name("Cover and simmer until the pasta is al dente (see note).").build(),
        Step.builder().name("Stir in the chicken, bell pepper, spinach and parsley, and stir to wilt the spinach. Remove from the heat and sprinkle with the feta cheese. Taste and add salt, if desired. Serve.").build()
    );

    assertThat(recipe.getSteps()).isEqualTo(steps);
  }

  @Test
  public void execute_importStepsFromBigParagraph() {
    Recipe recipe = subject.execute("https://www.cookincanuck.com/baked-tortellini-with-turkey-butternut-squash-chard-recipe/");

    List<Step> steps = Arrays.asList(
        Step.builder().name("Preheat the oven to 350 degrees F.").build(),
        Step.builder().name("Bring a large saucepan of salted water to a boil over high heat. Add the butternut squash cubes and cook until tender when pierced with a fork, about 10 minutes. Using a slotted spoon, transfer the squash to a bowl and mash with the back of a fork. Set aside.").build(),
        Step.builder().name("Add the tortellini to the boiling water and cook for 2 minutes less than directed by package instructions. Drain the tortellini and transfer to a large bowl.").build(),
        Step.builder().name("To the tortellini, add 1 cup tomato sauce, mashed butternut squash, turkey (or chicken) and chard. Stir to combine.").build(),
        Step.builder().name("Spread ¾ cup tomato sauce on the bottom of a 9- by 12-inch (or 9- by 13 inch) baking dish.").build(),
        Step.builder().name("Transfer half or the tortellini mixture to the baking dish and spread evenly. Top with ¾ cup tomato sauce.").build(),
        Step.builder().name("Transfer the remaining tortellini mixture to the baking dish, spreading evenly. Spread remaining ¾ cup tomato sauce over top.").build(),
        Step.builder().name("Sprinkle the topping evenly on top of the tortellini. Bake, uncovered, until the cheese is melted and the casserole is heated through, about 30 minutes. Serve.").build(),
        Step.builder().name("The topping:").build(),
        Step.builder().name("In a medium bowl, stir together the Parmesan cheese, sage and pecans.").build()
    );

    assertThat(recipe.getSteps()).isEqualTo(steps);
  }
}