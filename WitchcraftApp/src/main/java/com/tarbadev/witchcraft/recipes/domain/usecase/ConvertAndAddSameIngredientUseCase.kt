package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.converter.IngredientConverter
import org.springframework.stereotype.Component

import java.util.Arrays
import java.util.stream.Collectors

@Component
class ConvertAndAddSameIngredientUseCase(private val ingredientConverter: IngredientConverter) {

    fun execute(allIngredients: List<Ingredient>): List<Ingredient> {
        val ingredientsByNameAndUnit = allIngredients
            .groupBy { ingredient -> Arrays.asList<Any>(ingredient.name, ingredient.unit) }

        val ingredientsByName = ingredientsByNameAndUnit.values
            .map { e -> e.stream().reduce { a, b -> a.addQuantity(b.quantity) }.orElse(null) }
            .groupBy { it.name }

        return ingredientsByName.entries
            .map { entrySet ->
                entrySet.value
                    .reduce { ingredient1, ingredient2 -> ingredientConverter.addToHighestUnit(ingredient1, ingredient2) }
            }
    }
}
