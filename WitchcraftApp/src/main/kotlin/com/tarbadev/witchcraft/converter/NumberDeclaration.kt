package com.tarbadev.witchcraft.converter

import systems.uom.common.USCustomary.FLUID_OUNCE
import systems.uom.common.USCustomary.LITER
import tech.units.indriya.ComparableQuantity
import tech.units.indriya.quantity.Quantities
import javax.measure.Unit
import javax.measure.quantity.Volume

val MILLILITER: Unit<Volume> = LITER.divide(1000.0)

val Number.milliliter: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, MILLILITER)

val Number.fluid_ounce: ComparableQuantity<Volume>
  get() = Quantities.getQuantity(this, FLUID_OUNCE)