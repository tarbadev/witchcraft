package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientFromStringUseCaseTest {
  private IngredientFromStringUseCase subject;

  @Before
  public void setUp() {
    subject = new IngredientFromStringUseCase();
  }

  @Test
  public void execute() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("1 cup something")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_transformsSpecialFractionCharacters() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0 / 2.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("½ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0 / 3.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("⅓ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(2.0 / 3.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("⅔ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0 / 3.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("⅓ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0 / 4.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("¼ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(3.0 / 4.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("¾ cup something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0 / 8.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("⅛ cup something")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_convertsLongUnitToShort() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("oz")
        .build();
    assertThat(subject.execute("1 ounce something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("lb")
        .build();
    assertThat(subject.execute("1 pound something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("tbsp")
        .build();
    assertThat(subject.execute("1 tablespoon something")).isEqualTo(expectedIngredient);

    expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("tsp")
        .build();
    assertThat(subject.execute("1 teaspoon something")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_removesPluralForUnits() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("1 cups something")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_acceptsNoUnit() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.0)
        .unit("")
        .build();
    assertThat(subject.execute("1 something")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_acceptsParenthesesInName() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("(something)")
        .quantity(1.0)
        .unit("cup")
        .build();
    assertThat(subject.execute("1 cup (something)")).isEqualTo(expectedIngredient);
  }

  @Test
  public void execute_acceptsFractions() {
    Ingredient expectedIngredient = Ingredient.builder()
        .name("something")
        .quantity(1.5)
        .unit("cup")
        .build();
    assertThat(subject.execute("1 1/2 cup something")).isEqualTo(expectedIngredient);
  }
}