package com.tarbadev.witchcraft.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class UnitConverterTest {
    private val unitConverter: UnitConverter = UnitConverter()

    @Test
    fun convert_OzToLb() {
        val oz = 16.0
        val expectedLb = 1.0

        assertEquals(expectedLb, unitConverter.convert(oz, UnitConverter.UnitName.OZ.name, UnitConverter.UnitName.LB.name))
    }

    @Test
    fun convert_tspToCup() {
        val tsp = 48.0
        val cup = 1.0

        assertEquals(cup, unitConverter.convert(tsp, UnitConverter.UnitName.TSP.name, UnitConverter.UnitName.CUP.name))
    }

    @Test
    fun convert_tspToTbsp() {
        val tsp = 3.0
        val tbsp = 1.0

        assertEquals(tbsp, unitConverter.convert(tsp, UnitConverter.UnitName.TSP.name, UnitConverter.UnitName.TBSP.name))
    }

    @Test
    fun convertToHighestUnit_MlToOz() {
        val ml = 1.0
        val oz = 0.033814

        val expectedResult = AbstractMap.SimpleEntry<String, Double>(UnitConverter.UnitName.OZ.name, oz)
        val returnedValue = unitConverter.convertToHighestUnit(ml, UnitConverter.UnitName.ML.name, UnitConverter.UnitName.OZ.name)
        returnedValue.setValue(BigDecimal(returnedValue.value).setScale(6, RoundingMode.HALF_UP).toDouble())

        assertEquals(expectedResult, returnedValue)
    }

    @Test
    fun convertToHighestUnit_OzToMlReturnsOz() {
        val oz = 0.033814

        val expectedResult = AbstractMap.SimpleEntry<String, Double>(UnitConverter.UnitName.OZ.name, oz)
        val returnedValue = unitConverter.convertToHighestUnit(oz, UnitConverter.UnitName.OZ.name, UnitConverter.UnitName.ML.name)
        returnedValue.setValue(BigDecimal(returnedValue.value).setScale(6, RoundingMode.HALF_UP).toDouble())

        assertEquals(expectedResult, returnedValue)
    }

    @Test
    fun convertToHighestUnit_TspToCup() {
        val tsp = 3.0
        val cup = 0.0625

        val expectedResult = AbstractMap.SimpleEntry<String, Double>(UnitConverter.UnitName.CUP.name, cup)
        val returnedValue = unitConverter.convertToHighestUnit(tsp, UnitConverter.UnitName.TSP.name, UnitConverter.UnitName.CUP.name)

        assertEquals(expectedResult, returnedValue)
    }

    @Test
    fun convertToHighestUnit_TbspToCup() {
        val tbsp = 10.0
        val cup = 0.625

        val expectedResult = AbstractMap.SimpleEntry<String, Double>(UnitConverter.UnitName.CUP.name, cup)
        val returnedValue = unitConverter.convertToHighestUnit(tbsp, UnitConverter.UnitName.TBSP.name, UnitConverter.UnitName.CUP.name)

        assertEquals(expectedResult, returnedValue)
    }

    @Test
    fun convertToHighestUnit_TspToTbsp() {
        val tsp = 3.0
        val tbsp = 1.0

        val expectedResult = AbstractMap.SimpleEntry<String, Double>(UnitConverter.UnitName.TBSP.name, tbsp)
        val returnedValue = unitConverter.convertToHighestUnit(tsp, UnitConverter.UnitName.TSP.name, UnitConverter.UnitName.TBSP.name)

        assertEquals(expectedResult, returnedValue)
    }
}