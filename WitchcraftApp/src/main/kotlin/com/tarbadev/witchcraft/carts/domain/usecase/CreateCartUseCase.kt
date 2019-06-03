package com.tarbadev.witchcraft.carts.domain.usecase

import com.tarbadev.witchcraft.converter.IngredientConverter
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.springframework.stereotype.Component

import java.util.Arrays.asList

@Component
class CreateCartUseCase(
    private val cartRepository: CartRepository,
    private val ingredientConverter: IngredientConverter
) {

    fun execute(recipes: List<Recipe>): Cart {
        return cartRepository.save(Cart(recipes = recipes, items = getItemsFromRecipe(recipes)))
    }

    private fun getItemsFromRecipe(recipes: List<Recipe>): List<Item> {
        val allIngredients = recipes.flatMap { recipe -> recipe.ingredients }

        val ingredientsByNameAndUnit = allIngredients.groupBy { asList(it.name, it.unit) }

        val ingredientsByName = ingredientsByNameAndUnit.values
                .map { it.reduce { a, b -> a.addQuantity(b.quantity) } }
                .groupBy { it.name }

        println("Ingredients to calculate = " + ingredientsByName.values.filter { values -> values.size > 1 })

        val ingredients = ingredientsByName.entries
                .map {
                    it.value.reduce { ingredient1, ingredient2 -> ingredientConverter.addToHighestUnit(ingredient1, ingredient2) }
                }

        return ingredients.map { Item.fromIngredient(it) }
    }
}
