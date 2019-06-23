package com.tarbadev.witchcraft.converter

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.springframework.stereotype.Component
import systems.uom.common.USCustomary.OUNCE
import tech.units.indriya.ComparableQuantity

@Component
class IngredientConverter {

  fun addToHighestUnit(ingredient1: Ingredient, ingredient2: Ingredient): Ingredient {
    when {
      ingredient1.quantity.getUnit().isCompatible(ingredient2.quantity.getUnit()) -> {
        val result = if ((ingredient1.quantity as ComparableQuantity<Nothing>).isGreaterThanOrEqualTo(ingredient2.quantity as ComparableQuantity<Nothing>)) {
          ingredient1.quantity.add(ingredient2.quantity)
        } else {
          ingredient2.quantity.add(ingredient1.quantity)
        }

        return Ingredient(
            name = ingredient1.name,
            quantity = result
        )
      }
      ingredient1.quantity.getUnit() == OUNCE -> return addToHighestUnit(
          getIngredientWithFluidOunceUnit(ingredient1),
          ingredient2
      )
      ingredient2.quantity.getUnit() == OUNCE -> return addToHighestUnit(
          ingredient1,
          getIngredientWithFluidOunceUnit(ingredient2)
      )
      else -> throw IncompatibleIngredientUnitException(ingredient1, ingredient2)
    }
  }

  private fun getIngredientWithFluidOunceUnit(ingredient: Ingredient) =
      ingredient.copy(quantity = getQuantity(ingredient.quantity.getValue().toDouble(), "fl oz"))

  data class IncompatibleIngredientUnitException(val ingredient1: Ingredient, val ingredient2: Ingredient)
    : Exception("Cannot convert ${ingredient1.quantity.getUnit().getName()} and ${ingredient2.quantity.getUnit().getName()}, units are incompatible")
}

