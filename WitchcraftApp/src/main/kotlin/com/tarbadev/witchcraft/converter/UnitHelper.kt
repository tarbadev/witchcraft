package com.tarbadev.witchcraft.converter

import systems.uom.common.CGS
import systems.uom.common.USCustomary
import tech.units.indriya.AbstractUnit
import tech.units.indriya.ComparableQuantity
import javax.measure.Unit

object UnitHelper {
  private val unitMappings = listOf(
      UnitMapping(USCustomary.LITER, "l", "getLiter"),
      UnitMapping(CENTILITER, "cl", "getCentiliter"),
      UnitMapping(MILLILITER, "ml", "getMilliliter"),
      UnitMapping(VERRE, "verre", "getVerre"),
      UnitMapping(USCustomary.FLUID_OUNCE, "fl oz", "getFluid_ounce"),
      UnitMapping(USCustomary.CUP, "cup", "getCup"),
      UnitMapping(USCustomary.TABLESPOON, "tbsp", "getTablespoon"),
      UnitMapping(USCustomary.TEASPOON, "tsp", "getTeaspoon"),
      UnitMapping(USCustomary.OUNCE, "oz", "getOunce"),
      UnitMapping(USCustomary.POUND, "lb", "getPound"),
      UnitMapping(CGS.GRAM, "g", "getGram"),
      UnitMapping(AbstractUnit.ONE, "", "getUnit")
  )

  fun getQuantity(unit: String, quantity: Double): ComparableQuantity<*> {
    if (unit.isEmpty()) {
      return quantity.unit
    }

    val forName = Class.forName("com.tarbadev.witchcraft.converter.NumberDeclarationKt")
    val unitMapping = getUnitMappingByShortName(unit)
        ?: throw UnitNotFoundException(unit)
    val method = forName.getMethod(unitMapping.methodName, Number::class.java)
    return method.invoke(0, quantity) as ComparableQuantity<*>
  }

  fun getUnitShortName(quantity: ComparableQuantity<*>): String {
    val unitMapping = getUnitMappingByUnit(quantity.getUnit())
        ?: throw UnitNotFoundException(quantity.getUnit().toString())
    return unitMapping.shortName
  }

  fun getUnitMappingByShortName(shortName: String): UnitMapping? {
    return unitMappings.firstOrNull { it.shortName == shortName }
  }

  private fun getUnitMappingByUnit(unit: Unit<*>): UnitMapping? {
    return unitMappings.firstOrNull { it.unit == unit }
  }
  data class UnitMapping(val unit: Unit<*>, val shortName: String, val methodName: String)
  data class UnitNotFoundException(val unit: String) : Exception("Unit '$unit' is not supported")
}