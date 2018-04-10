package com.tarbadev.witchcraft.domain.converter;

public class TspToCupConverter implements IConverter {
  private static final Double TSP_IN_ONE_CUP = 48.0;

  @Override
  public Double convert(Double number) {
    return number / TSP_IN_ONE_CUP;
  }
}
