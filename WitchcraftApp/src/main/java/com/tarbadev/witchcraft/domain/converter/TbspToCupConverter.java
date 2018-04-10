package com.tarbadev.witchcraft.domain.converter;

public class TbspToCupConverter implements IConverter {
  private static final Double TBSP_IN_ONE_CUP = 16.0;

  @Override
  public Double convert(Double number) {
    return number / TBSP_IN_ONE_CUP;
  }
}
