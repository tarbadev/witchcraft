package com.tarbadev.witchcraft.carts.rest.entity

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CartResponseTest {
  @Test
  fun fromCart() {
    val item = Item(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.0,
        unit = "lb"
    )
    val recipe = Recipe(
        id = 1,
        originUrl = "http://origin",
        imgUrl = "http://imgurl",
        name = "raclette",
        favorite = true,
        ingredients = emptyList(),
        steps = emptyList(),
        isArchived = false
    )
    val cart = Cart(
        12,
        listOf(recipe),
        listOf(item),
        LocalDateTime.now()
    )
    val cartResponse = CartResponse(
        id = cart.id,
        recipes = listOf(RecipeResponse.fromRecipe(recipe)),
        items = listOf(ItemResponse.fromItem(item)),
        createdAt = cart.createdAt
    )
    assertThat(CartResponse.fromCart(cart)).isEqualTo(cartResponse)
  }
}