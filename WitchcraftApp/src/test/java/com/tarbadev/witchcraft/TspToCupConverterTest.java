package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TspToCupConverterTest {
  private TspToCupConverter subject;

  @Before
  public void setUp() {
    subject = new TspToCupConverter();
  }

  @Test
  public void convert() {
    Double tsp = 48.0;
    Double cup = 1.0;

    assertThat(subject.convert(tsp)).isEqualTo(cup);

    tsp = 3.0;
    cup = 0.0625;

    assertThat(subject.convert(tsp)).isEqualTo(cup);
  }
}
