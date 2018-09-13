package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import com.tarbadev.witchcraft.domain.entity.Ingredient;
import com.tarbadev.witchcraft.domain.usecase.ConvertAndAddSameIngredientUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ConvertAndAddSameIngredientUseCaseTest {
  @Autowired private IngredientConverter ingredientConverter;

  private ConvertAndAddSameIngredientUseCase subject;

  @Before
  public void setUp() {
    subject = new ConvertAndAddSameIngredientUseCase(ingredientConverter);
    Mockito.reset(ingredientConverter);
  }

  @Test
  public void execute() {
    List<Ingredient> allIngredients = Arrays.asList(
        Ingredient.builder().name("olive oil").quantity(0.5).unit("cup").build(),
        Ingredient.builder().name("olive oil").quantity(3.0).unit("tbsp").build()
    );

    Ingredient expectedIngredient = Ingredient.builder()
        .name("olive oil")
        .quantity(0.5625)
        .unit("cup")
        .build();

    given(ingredientConverter.addToHighestUnit(allIngredients.get(0), allIngredients.get(1)))
        .willReturn(expectedIngredient);

    assertThat(subject.execute(allIngredients)).isEqualTo(
        Collections.singletonList(expectedIngredient)
    );
  }
}