package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class CartEntityTest {
  @Test
  fun fromCart() {
    val cartEntity = CartEntity(
        23,
        Instant.now(),
        setOf(RecipeEntity()),
        listOf(ItemEntity())
    )

    val cart = Cart(
        cartEntity.id,
        cartEntity.recipes.map { it.toRecipe() },
        cartEntity.items.map { it.toItem() },
        cartEntity.createdAt
    )

    assertThat(CartEntity.fromCart(cart)).isEqualTo(cartEntity)
  }
}