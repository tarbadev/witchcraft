package com.tarbadev.witchcraft.recipes.domain.entity

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.converter.UnitHelper.getUnitShortName
import tech.units.indriya.ComparableQuantity


data class Ingredient(
    val id: Int = 0,
    val name: String = "",
    val quantity: ComparableQuantity<*>
) {

  fun addQuantity(quantity: ComparableQuantity<*>): Ingredient {
    return this.copy(
        quantity = getQuantity(
            this.quantity.getValue().toDouble().plus(quantity.getValue().toDouble()),
            getUnitShortName(this.quantity)
        )
    )
  }
}
