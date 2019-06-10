package com.tarbadev.witchcraft.converter

import com.tarbadev.witchcraft.converter.UnitConverter.UnitName.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.units.indriya.ComparableQuantity
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.AbstractMap.SimpleEntry
import javax.measure.quantity.Volume

class UnitConverterTest {
  private val unitConverter: UnitConverter = UnitConverter()

  @Test
  fun convert_OzToLb() {
    val oz = 16.0
    val expectedLb = 1.0

    assertEquals(expectedLb, unitConverter.convert(oz, OZ.value, LB.value))
  }

  @Test
  fun convert_tspToCup() {
    val tsp = 48.0
    val cup = 1.0

    assertEquals(cup, unitConverter.convert(tsp, TSP.value, CUP.value))
  }

  @Test
  fun convert_tspToTbsp() {
    val tsp = 3.0
    val tbsp = 1.0

    assertEquals(tbsp, unitConverter.convert(tsp, TSP.value, TBSP.value))
  }

  @Test
  fun convertToHighestUnit_MlAndFlOz_ReturnsFlOz() {
    val input1 = 1.milliliter
    val input2 = 1.fluid_ounce

    val returnedValue = setScale(unitConverter.convertToHighestUnit(input1, input2), 6)
    val expectedResult = setScale(1.033814.fluid_ounce, 6)

    assertThat(returnedValue).isEqualTo(expectedResult)
  }

  @Test
  fun convertToHighestUnit_OzToMlReturnsOz() {
    val oz = 0.033814

    val expectedResult = SimpleEntry<String, Double>(OZ.value, oz)
    val returnedValue = unitConverter.convertToHighestUnit(oz, OZ.value, ML.value)
    returnedValue.setValue(BigDecimal(returnedValue.value).setScale(6, RoundingMode.HALF_UP).toDouble())

    assertEquals(expectedResult, returnedValue)
  }

  @Test
  fun convertToHighestUnit_TspToCup() {
    val tsp = 3.0
    val cup = 0.0625

    val expectedResult = SimpleEntry<String, Double>(CUP.value, cup)
    val returnedValue = unitConverter.convertToHighestUnit(tsp, TSP.value, CUP.value)

    assertEquals(expectedResult, returnedValue)
  }

  @Test
  fun convertToHighestUnit_TbspToCup() {
    val tbsp = 10.0
    val cup = 0.625

    val expectedResult = SimpleEntry<String, Double>(CUP.value, cup)
    val returnedValue = unitConverter.convertToHighestUnit(tbsp, TBSP.value, CUP.value)

    assertEquals(expectedResult, returnedValue)
  }

  @Test
  fun convertToHighestUnit_TspToTbsp() {
    val tsp = 3.0
    val tbsp = 1.0

    val expectedResult = SimpleEntry<String, Double>(TBSP.value, tbsp)
    val returnedValue = unitConverter.convertToHighestUnit(tsp, TSP.value, TBSP.value)

    assertEquals(expectedResult, returnedValue)
  }

  private fun setScale(number: ComparableQuantity<Volume>, scale: Int): BigDecimal {
    return BigDecimal(number.value.toDouble()).setScale(scale, RoundingMode.HALF_DOWN)
  }
}