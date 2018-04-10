package com.tarbadev.witchcraft.domain.converter;

import com.tarbadev.witchcraft.domain.converter.UnitConverter;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UnitConverterTest {
  private UnitConverter subject;

  @Before
  public void setUp() {
    subject = new UnitConverter();
  }

  @Test
  public void convert_OzToLb() {
    Double oz = 16.0;
    Double expectedLb = 1.0;

    assertThat(subject.convert(oz, UnitConverter.UnitName.OZ.getName(), UnitConverter.UnitName.LB.getName())).isEqualTo(expectedLb);
  }

  @Test
  public void convert_tspToCup() {
    Double tsp = 48.0;
    Double cup = 1.0;

    assertThat(subject.convert(tsp, UnitConverter.UnitName.TSP.getName(), UnitConverter.UnitName.CUP.getName())).isEqualTo(cup);
  }

  @Test
  public void convertToHighestUnit_MlToOz() {
    Double ml = 1.0;
    Double oz = 0.033814;

    Map.Entry<String, Double> expectedResult = new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.OZ.getName(), oz);
    Map.Entry<String, Double> returnedValue = subject.convertToHighestUnit(ml, UnitConverter.UnitName.ML.getName(), UnitConverter.UnitName.OZ.getName());
    returnedValue.setValue(new BigDecimal(returnedValue.getValue()).setScale(6, RoundingMode.HALF_UP).doubleValue());

    assertThat(returnedValue).isEqualTo(expectedResult);
  }

  @Test
  public void convertToHighestUnit_OzToMlReturnsOz() {
    Double oz = 0.033814;

    Map.Entry<String, Double> expectedResult = new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.OZ.getName(), oz);
    Map.Entry<String, Double> returnedValue = subject.convertToHighestUnit(oz, UnitConverter.UnitName.OZ.getName(), UnitConverter.UnitName.ML.getName());
    returnedValue.setValue(new BigDecimal(returnedValue.getValue()).setScale(6, RoundingMode.HALF_UP).doubleValue());

    assertThat(returnedValue).isEqualTo(expectedResult);
  }

  @Test
  public void convertToHighestUnit_TspToCup() {
    Double tsp = 3.0;
    Double cup = 0.0625;

    Map.Entry<String, Double> expectedResult = new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.CUP.getName(), cup);
    Map.Entry<String, Double> returnedValue = subject.convertToHighestUnit(tsp, UnitConverter.UnitName.TSP.getName(), UnitConverter.UnitName.CUP.getName());

    assertThat(returnedValue).isEqualTo(expectedResult);
  }

  @Test
  public void convertToHighestUnit_TbspToCup() {
    Double tbsp = 10.0;
    Double cup = 0.625;

    Map.Entry<String, Double> expectedResult = new AbstractMap.SimpleEntry<>(UnitConverter.UnitName.CUP.getName(), cup);
    Map.Entry<String, Double> returnedValue = subject.convertToHighestUnit(tbsp, UnitConverter.UnitName.TBSP.getName(), UnitConverter.UnitName.CUP.getName());

    assertThat(returnedValue).isEqualTo(expectedResult);
  }
}