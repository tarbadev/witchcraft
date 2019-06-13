package com.tarbadev.witchcraft.converter

import systems.uom.common.CGS.GRAM
import systems.uom.common.USCustomary.*
import tech.units.indriya.AbstractUnit.ONE
import tech.units.indriya.ComparableQuantity
import tech.units.indriya.quantity.Quantities
import javax.measure.Unit
import javax.measure.quantity.Dimensionless
import javax.measure.quantity.Mass
import javax.measure.quantity.Volume

val CENTILITER: Unit<Volume> = LITER.divide(100.0)
val MILLILITER: Unit<Volume> = CENTILITER.divide(10.0)
val VERRE: Unit<Volume> = CENTILITER.multiply(20.0)

val Number.liter: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, LITER)
val Number.centiliter: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, CENTILITER)
val Number.milliliter: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, MILLILITER)
val Number.verre: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, VERRE)
val Number.fluid_ounce: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, FLUID_OUNCE)
val Number.cup: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, CUP)
val Number.tablespoon: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, TABLESPOON)
val Number.teaspoon: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, TEASPOON)


val Number.ounce: ComparableQuantity<Mass>
  get() = Quantities.getQuantity(this, OUNCE)
val Number.pound: ComparableQuantity<Mass>
  get() = Quantities.getQuantity(this, POUND)
val Number.gram: ComparableQuantity<Mass>
  get() = Quantities.getQuantity(this, GRAM)


val Number.unit: ComparableQuantity<Dimensionless>
  get() = Quantities.getQuantity(this, ONE)