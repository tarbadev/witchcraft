package com.tarbadev.witchcraft.converter

import org.springframework.stereotype.Component
import tech.units.indriya.ComparableQuantity
import java.util.*
import java.util.AbstractMap.SimpleEntry
import javax.measure.quantity.Volume

@Component
class UnitConverter {

  private val units = object : HashMap<String, Unit>() {
    init {
      put(UnitName.OZ.value, Unit.OZ)
      put(UnitName.ML.value, Unit.ML)
      put(UnitName.TSP.value, Unit.TSP)
      put(UnitName.CUP.value, Unit.CUP)
      put(UnitName.TBSP.value, Unit.TBSP)
    }
  }

  enum class UnitName(val value: String) {
    LB("lb"),
    CUP("cup"),
    TSP("tsp"),
    TBSP("tbsp"),
    OZ("oz"),
    ML("ml");
  }

  private enum class Unit(val value: Int) {
    OZ(1),
    ML(0),
    CUP(1),
    TSP(0),
    TBSP(0);

    internal fun isSuperiorTo(unit: Unit): Boolean = value > unit.value
  }

  fun convert(number: Double, unitIn: String, unitOut: String): Double {
    val converter = getConverter(unitIn, unitOut)
    return converter!!.convert(number)
  }

  private fun getConverter(unitIn: String, unitOut: String): IConverter? {
    var converter: IConverter? = null

    if (unitIn == UnitName.OZ.value && unitOut == UnitName.LB.value) {
      converter = OzToLbConverter()
    } else if (unitIn == UnitName.ML.value && unitOut == UnitName.OZ.value) {
      converter = MlToOzConverter()
    } else if (unitIn == UnitName.TSP.value && unitOut == UnitName.CUP.value) {
      converter = TspToCupConverter()
    } else if (unitIn == UnitName.TBSP.value && unitOut == UnitName.CUP.value) {
      converter = TbspToCupConverter()
    } else if (unitIn == UnitName.TSP.value && unitOut == UnitName.TBSP.value) {
      converter = TspToTbspConverter()
    }

    return converter
  }

  fun convertToHighestUnit(quantity: Double, unitIn: String, otherUnit: String): SimpleEntry<String, Double> {
    val conversionFactors = getConversionFactors(quantity, unitIn, otherUnit)

    return if (conversionFactors.unitOut == unitIn) {
      SimpleEntry(conversionFactors.unitOut, conversionFactors.quantity)
    } else {
      val converter = getConverter(conversionFactors.unitIn, conversionFactors.unitOut)
      SimpleEntry(conversionFactors.unitOut, converter!!.convert(conversionFactors.quantity))
    }
  }

  private fun getConversionFactors(quantity: Double, unit1: String, unit2: String): ConversionFactors {
    val highestUnitList = units.entries
        .filter { it.key == unit1 || it.key == unit2 }
        .toList()

    val highestUnit = highestUnitList
        .reduce { u1, u2 ->
          if (u1.value.isSuperiorTo(u2.value))
            u1
          else
            u2
        }
        .key

    val unitIn: String
    val unitOut: String

    if (highestUnit == unit1) {
      unitIn = unit2
      unitOut = unit1
    } else {
      unitIn = unit1
      unitOut = unit2
    }

    return ConversionFactors(unitIn, quantity, unitOut)
  }

  fun convertToHighestUnit(input1: ComparableQuantity<Volume>, input2: ComparableQuantity<Volume>): ComparableQuantity<Volume> {
    return if (input1.isGreaterThanOrEqualTo(input2)) {
      input1.add(input2)
    } else {
      input2.add(input1)
    }
  }

  private data class ConversionFactors(
      val unitIn: String,
      val quantity: Double,
      val unitOut: String
  )
}
