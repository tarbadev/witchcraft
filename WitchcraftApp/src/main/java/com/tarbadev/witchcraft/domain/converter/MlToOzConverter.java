package com.tarbadev.witchcraft.domain.converter;

public class MlToOzConverter implements IConverter {
  private static final Double ML_IN_ONE_OZ = 29.5735;

  @Override
  public Double convert(Double number) {
    return number / ML_IN_ONE_OZ;
  }
}
