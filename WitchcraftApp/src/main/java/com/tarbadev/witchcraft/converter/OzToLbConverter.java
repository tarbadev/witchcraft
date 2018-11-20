package com.tarbadev.witchcraft.converter;

public class OzToLbConverter implements IConverter {
  private static final Double OZ_IN_ONE_LB = 16.0;

  public Double convert(Double oz) {
    return oz / OZ_IN_ONE_LB;
  }
}
