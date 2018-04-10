package com.tarbadev.witchcraft.domain.converter;

import com.tarbadev.witchcraft.domain.converter.OzToLbConverter;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OzToLbConverterTest {
  private OzToLbConverter ozToLbConverter;

  @Before
  public void setUp() {
    ozToLbConverter = new OzToLbConverter();
  }

  @Test
  public void convert() {
    Double oz = 16.0;
    Double expectedLb = 1.0;

    assertThat(ozToLbConverter.convert(oz)).isEqualTo(expectedLb);

    Double oz2 = 20.0;
    Double expectedLb2 = 1.25;

    assertThat(ozToLbConverter.convert(oz2)).isEqualTo(expectedLb2);

    Double oz3 = 5.0;
    Double expectedLb3 = 0.3125;

    assertThat(ozToLbConverter.convert(oz3)).isEqualTo(expectedLb3);
  }
}