package com.tarbadev.witchcraft.domain.converter;

public class TspToTbspConverter implements IConverter {
  private static final Double TSP_IN_ONE_TBSP = 3.0;

  @Override
  public Double convert(Double number) {
    return number / TSP_IN_ONE_TBSP;
  }
}
