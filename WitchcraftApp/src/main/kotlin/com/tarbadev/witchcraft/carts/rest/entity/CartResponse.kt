package com.tarbadev.witchcraft.carts.rest.entity

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import java.time.LocalDateTime

data class CartResponse(
    val id: Int,
    val recipes: List<RecipeResponse>,
    val items: List<ItemResponse>,
    var createdAt: LocalDateTime
) {
  companion object {
    fun fromCart(cart: Cart): CartResponse {
      return CartResponse(
          cart.id,
          cart.recipes.map { RecipeResponse.fromRecipe(it) },
          cart.items.map { ItemResponse.fromItem(it) },
          cart.createdAt
      )
    }
  }
}