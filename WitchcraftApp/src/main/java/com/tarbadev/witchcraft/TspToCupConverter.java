package com.tarbadev.witchcraft;

public class TspToCupConverter implements IConverter {
  private static final Double CUP_IN_ONE_TSP = 48.0;

  @Override
  public Double convert(Double number) {
    return number / CUP_IN_ONE_TSP;
  }
}
