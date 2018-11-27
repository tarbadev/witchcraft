package com.tarbadev.witchcraft.converter

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.springframework.stereotype.Component

@Component
class IngredientConverter(private val unitConverter: UnitConverter) {

    fun addToHighestUnit(ingredient1: Ingredient, ingredient2: Ingredient): Ingredient {
        val highestUnit = unitConverter.convertToHighestUnit(ingredient1.quantity, ingredient1.unit, ingredient2.unit)
        return if (highestUnit.key == ingredient1.unit) {
            val result = unitConverter.convert(ingredient2.quantity, ingredient2.unit, ingredient1.unit)
            ingredient1.addQuantity(result)
        } else
            Ingredient(
                name = ingredient1.name,
                unit = highestUnit.key,
                quantity = highestUnit.value
            )
                .addQuantity(ingredient2.quantity)
    }
}
