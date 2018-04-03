package com.tarbadev.witchcraft;

public class OzToLbConverter implements IConverter {
  private static final Double LB_IN_OZ = 16.0;

  public Double convert(Double oz) {
    return oz / LB_IN_OZ;
  }
}
