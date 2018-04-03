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
  private enum Unit {
    OZ(1),
    ML(0);

    private final int value;

    private boolean isSuperiorTo(Unit unit) { return value > unit.getValue(); }
  }

  public static final String OZ = "oz";
  public static final String LB = "lb";
  public static final String ML = "ml";

  private Map<String, Unit> units = new HashMap<String, Unit>() {
    {
      put(OZ, Unit.OZ);
      put(ML, Unit.ML);
    }
  };

  public Double convert(Double number, String unitIn, String unitOut) {
    IConverter converter = getConverter(unitIn, unitOut);
    return converter.convert(number);
  }

  private IConverter getConverter(String unitIn, String unitOut) {
    IConverter converter = null;

    if (unitIn.equals(OZ) && unitOut.equals(LB)) {
      converter = new OzToLbConverter();
    } else if (unitIn.equals(ML) && unitOut.equals(OZ)) {
      converter = new MlToOzConverter();
    }

    return converter;
  }

  public Map.Entry<String, Double> convertToHighestUnit(double quantity1, String unit1, double quantity2, String unit2) {
    ConversionFactors conversionFactors = getConversionFactors(quantity1, unit1, quantity2, unit2);
    IConverter converter = getConverter(conversionFactors.getUnitIn(), conversionFactors.getUnitOut());
    
    return new AbstractMap.SimpleEntry<>(conversionFactors.getUnitOut(), converter.convert(conversionFactors.getQuantityIn()));
  }

  private ConversionFactors getConversionFactors(double quantity1, String unit1, double quantity2, String unit2) {
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
    Double quantityIn;
    String unitOut;

    if (highestUnit.equals(unit1)) {
      unitIn = unit2;
      quantityIn = quantity2;
      unitOut = unit1;
    } else {
      unitIn = unit1;
      quantityIn = quantity1;
      unitOut = unit2;
    }

    return new ConversionFactors(unitIn, quantityIn, unitOut);
  }

  @Value
  private class ConversionFactors {
    private String unitIn;
    private Double quantityIn;
    private String unitOut;
  }
}
