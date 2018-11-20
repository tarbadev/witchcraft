package com.tarbadev.witchcraft.domain.converter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TspToTbspConverterTest {
  private TspToTbspConverter subject;

  @Before
  public void setUp() {
    subject = new TspToTbspConverter();
  }

  @Test
  public void convert() {
    Double tsp = 12.0;
    Double tbsp = 4.0;

    assertThat(subject.convert(tsp)).isEqualTo(tbsp);

    tsp = 3.0;
    tbsp = 1.0;

    assertThat(subject.convert(tsp)).isEqualTo(tbsp);
  }
}
