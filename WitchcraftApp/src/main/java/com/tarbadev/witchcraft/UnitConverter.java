package com.tarbadev.witchcraft;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class UnitConverter {
  @Getter
  @AllArgsConstructor
  public enum UnitName {
    LB("lb"),
    CUP("cup"),
    TSP("tsp"),
    OZ("oz"),
    ML("ml");

    private final String name;
  }

  @Getter
  @AllArgsConstructor
  private enum Unit {
    OZ(1),
    ML(0),
    CUP(1),
    TSP(0);

    private final int value;

    private boolean isSuperiorTo(Unit unit) { return value > unit.getValue(); }
  }

  private Map<String, Unit> units = new HashMap<String, Unit>() {
    {
      put(UnitName.OZ.getName(), Unit.OZ);
      put(UnitName.ML.getName(), Unit.ML);
      put(UnitName.TSP.getName(), Unit.TSP);
      put(UnitName.CUP.getName(), Unit.CUP);
    }
  };

  public Double convert(Double number, String unitIn, String unitOut) {
    IConverter converter = getConverter(unitIn, unitOut);
    return converter.convert(number);
  }

  private IConverter getConverter(String unitIn, String unitOut) {
    IConverter converter = null;

    if (unitIn.equals(UnitName.OZ.getName()) && unitOut.equals(UnitName.LB.getName())) {
      converter = new OzToLbConverter();
    } else if (unitIn.equals(UnitName.ML.getName()) && unitOut.equals(UnitName.OZ.getName())) {
      converter = new MlToOzConverter();
    } else if (unitIn.equals(UnitName.TSP.getName()) && unitOut.equals(UnitName.CUP.getName())) {
      converter = new TspToCupConverter();
    }

    return converter;
  }

  public Map.Entry<String, Double> convertToHighestUnit(double quantity, String unitIn, String otherUnit) {
    ConversionFactors conversionFactors = getConversionFactors(quantity, unitIn, otherUnit);

    AbstractMap.SimpleEntry<String, Double> newValue;
    if (conversionFactors.getUnitOut().equals(unitIn)) {
      newValue = new AbstractMap.SimpleEntry<>(conversionFactors.getUnitOut(), conversionFactors.getQuantity());
    } else {
      IConverter converter = getConverter(conversionFactors.getUnitIn(), conversionFactors.getUnitOut());
      newValue = new AbstractMap.SimpleEntry<>(conversionFactors.getUnitOut(), converter.convert(conversionFactors.getQuantity()));
    }

    return newValue;
  }

  private ConversionFactors getConversionFactors(double quantity, String unit1, String unit2) {
    String highestUnit = units.entrySet().stream()
        .filter(entrySet -> entrySet.getKey().equals(unit1) || entrySet.getKey().equals(unit2))
        .reduce((u1, u2) -> {
          if (u1.getValue().isSuperiorTo(u2.getValue()))
            return u1;
          else
            return u2;
        })
        .map(Map.Entry::getKey)
        .orElse(null);

    String unitIn;
    String unitOut;

    if (highestUnit.equals(unit1)) {
      unitIn = unit2;
      unitOut = unit1;
    } else {
      unitIn = unit1;
      unitOut = unit2;
    }

    return new ConversionFactors(unitIn, quantity, unitOut);
  }

  @Value
  private class ConversionFactors {
    private String unitIn;
    private Double quantity;
    private String unitOut;
  }
}
