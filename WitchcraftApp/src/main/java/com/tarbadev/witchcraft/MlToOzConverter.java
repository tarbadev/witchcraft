package com.tarbadev.witchcraft;

public class MlToOzConverter implements IConverter {
  private static final Double ML_IN_OZ = 29.5735;

  @Override
  public Double convert(Double number) {
    return number / ML_IN_OZ;
  }
}
