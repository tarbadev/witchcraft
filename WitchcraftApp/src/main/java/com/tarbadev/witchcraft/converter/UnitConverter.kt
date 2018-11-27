package com.tarbadev.witchcraft.converter

import org.springframework.stereotype.Component
import java.util.*

@Component
class UnitConverter {

    private val units = object : HashMap<String, Unit>() {
        init {
            put(UnitName.OZ.name, Unit.OZ)
            put(UnitName.ML.name, Unit.ML)
            put(UnitName.TSP.name, Unit.TSP)
            put(UnitName.CUP.name, Unit.CUP)
            put(UnitName.TBSP.name, Unit.TBSP)
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

        if (unitIn == UnitName.OZ.name && unitOut == UnitName.LB.name) {
            converter = OzToLbConverter()
        } else if (unitIn == UnitName.ML.name && unitOut == UnitName.OZ.name) {
            converter = MlToOzConverter()
        } else if (unitIn == UnitName.TSP.name && unitOut == UnitName.CUP.name) {
            converter = TspToCupConverter()
        } else if (unitIn == UnitName.TBSP.name && unitOut == UnitName.CUP.name) {
            converter = TbspToCupConverter()
        } else if (unitIn == UnitName.TSP.name && unitOut == UnitName.TBSP.name) {
            converter = TspToTbspConverter()
        }

        return converter
    }

    fun convertToHighestUnit(quantity: Double, unitIn: String, otherUnit: String): AbstractMap.SimpleEntry<String, Double> {
        val conversionFactors = getConversionFactors(quantity, unitIn, otherUnit)

        return if (conversionFactors.unitOut == unitIn) {
            AbstractMap.SimpleEntry(conversionFactors.unitOut, conversionFactors.quantity)
        } else {
            val converter = getConverter(conversionFactors.unitIn, conversionFactors.unitOut)
            AbstractMap.SimpleEntry(conversionFactors.unitOut, converter!!.convert(conversionFactors.quantity))
        }
    }

    private fun getConversionFactors(quantity: Double, unit1: String, unit2: String): ConversionFactors {
        val highestUnit = units.entries
            .filter { it.key == unit1 || it.key == unit2 }
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

    private data class ConversionFactors(
        val unitIn: String,
        val quantity: Double,
        val unitOut: String
    )
}
