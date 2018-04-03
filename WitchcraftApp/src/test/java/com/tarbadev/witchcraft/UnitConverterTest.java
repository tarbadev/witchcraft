package com.tarbadev.witchcraft;

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

    assertThat(subject.convert(oz, UnitConverter.OZ, UnitConverter.LB)).isEqualTo(expectedLb);
  }

  @Test
  public void convertToHighestUnit_MlToOz() {
    Double oz = 0.033814;
    Double ml = 1.0;

    Map.Entry<String, Double> expectedResult = new AbstractMap.SimpleEntry<>(UnitConverter.OZ, 0.033814);
    Map.Entry<String, Double> returnedValue = subject.convertToHighestUnit(oz, UnitConverter.OZ, ml, UnitConverter.ML);
    returnedValue.setValue(new BigDecimal(returnedValue.getValue()).setScale(6, RoundingMode.HALF_UP).doubleValue());

    assertThat(returnedValue).isEqualTo(expectedResult);
  }
}