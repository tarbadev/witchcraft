package com.tarbadev.witchcraft.domain.converter;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import com.tarbadev.witchcraft.domain.converter.UnitConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.AbstractMap;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientConverterTest {
  @Autowired private UnitConverter unitConverter;

  private IngredientConverter subject;

  @Before
  public void setUp() {
    subject = new IngredientConverter(unitConverter);
    Mockito.reset(unitConverter);
  }

  @Test
  public void addToHighestUnit() {
    Ingredient ingredientOz = Ingredient.builder().quantity(10.0).unit(UnitConverter.UnitName.OZ.getName()).build();
    Ingredient ingredientMl = Ingredient.builder().quantity(10.0).unit(UnitConverter.UnitName.ML.getName()).build();
    Ingredient expectedIngredient = Ingredient.builder().unit(UnitConverter.UnitName.OZ.getName()).quantity(10.33814).build();

    given(unitConverter.convertToHighestUnit(ingredientMl.getQuantity(), ingredientMl.getUnit(), ingredientOz.getUnit()))
        .willReturn(new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.OZ.getName(), expectedIngredient.getQuantity() - ingredientOz.getQuantity()));

    assertThat(subject.addToHighestUnit(ingredientMl, ingredientOz)).isEqualTo(expectedIngredient);
  }

  @Test
  public void addToHighestUnit_OzToMl() {
    Ingredient ingredientOz = Ingredient.builder().quantity(10.0).unit(UnitConverter.UnitName.OZ.getName()).build();
    Ingredient ingredientMl = Ingredient.builder().quantity(10.0).unit(UnitConverter.UnitName.ML.getName()).build();
    Ingredient expectedIngredient = Ingredient.builder().unit(UnitConverter.UnitName.OZ.getName()).quantity(10.33814).build();

    given(unitConverter.convertToHighestUnit(ingredientOz.getQuantity(), ingredientOz.getUnit(), ingredientMl.getUnit()))
        .willReturn(new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.OZ.getName(), ingredientOz.getQuantity()));
    given(unitConverter.convert(ingredientMl.getQuantity(), ingredientMl.getUnit(), ingredientOz.getUnit())).willReturn(expectedIngredient.getQuantity() - ingredientOz.getQuantity());

    assertThat(subject.addToHighestUnit(ingredientOz, ingredientMl)).isEqualTo(expectedIngredient);
  }
}