package com.tarbadev.witchcraft.converter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TbspToCupConverterTest {
  private TbspToCupConverter subject;

  @Before
  public void setUp() {
    subject = new TbspToCupConverter();
  }

  @Test
  public void convert() {
    Double tbsp = 1.0;
    Double expectedCup = 0.0625;

    assertThat(subject.convert(tbsp)).isEqualTo(expectedCup);

    tbsp = 4.0;
    expectedCup = 0.25;

    assertThat(subject.convert(tbsp)).isEqualTo(expectedCup);
  }
}