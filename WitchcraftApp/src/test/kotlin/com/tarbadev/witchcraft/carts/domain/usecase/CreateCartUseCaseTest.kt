package com.tarbadev.witchcraft.carts.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import com.tarbadev.witchcraft.converter.IngredientConverter
import com.tarbadev.witchcraft.converter.milliliter
import com.tarbadev.witchcraft.converter.ounce
import com.tarbadev.witchcraft.converter.pound
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Arrays.asList

class CreateCartUseCaseTest {
    private val ingredientConverter: IngredientConverter = mock()
    private val cartRepository: CartRepository = mock()

    private var subject: CreateCartUseCase = CreateCartUseCase(cartRepository, ingredientConverter)

    @BeforeEach
    fun setUp() {
        reset(
            ingredientConverter,
            cartRepository
        )
    }

    @Test
    fun execute() {
        val recipes = listOf(Recipe(
            ingredients = listOf(
                Ingredient(
                    name = "Ingredient 1",
                    quantity = 2.2.pound
                ),
                Ingredient(
                    name = "Ingredient 2",
                    quantity = 10.ounce
                )
            ),
            steps = emptyList()
        ))
        val items = recipes
            .flatMap {
                it.ingredients.map { ingredient -> Item.fromIngredient(ingredient) }
            }
            .sortedBy { it.name }

        val cart = Cart(
            recipes = recipes,
            items = items
        )

        whenever(cartRepository.save(cart)).thenReturn(cart)

        assertEquals(cart, subject.execute(recipes))

        verify(cartRepository).save(cart)
    }

    @Test
    fun execute_addsItemsWithSameNameAndConvertUnit() {
        val recipes = listOf(
            Recipe(
                ingredients = listOf(
                    Ingredient(
                        name = "Ingredient 1",
                        quantity = 1.2.pound
                    ),
                    Ingredient(
                        name = "Ingredient 2",
                        quantity = 10.ounce
                    )
                ),
                steps = emptyList()
            ),
            Recipe(
                ingredients = listOf(
                    Ingredient(
                        name = "Ingredient 1",
                        quantity = 2.2.pound
                    ),
                    Ingredient(
                        name = "Ingredient 2",
                        quantity = 10.milliliter
                    )
                ),
                steps = emptyList()
            )
        )
        val items = listOf(
            Item(
                name = "Ingredient 1",
                quantity = 2.2 + 1.2,
                unit = "lb",
                enabled = true
            ),
            Item(
                name = "Ingredient 2",
                quantity = 10.33814,
                unit = "oz",
                enabled = true
            )
        )
        val cart = Cart(
            recipes = recipes,
            items = items
        )

        whenever(ingredientConverter.addToHighestUnit(recipes[0].ingredients[1], recipes[1].ingredients[1]))
            .thenReturn(Ingredient(name = "Ingredient 2", quantity = 10.33814.ounce))
        whenever(cartRepository.save(cart)).thenReturn(cart)

        assertEquals(cart, subject.execute(recipes))

        verify(cartRepository).save(cart)
    }
}