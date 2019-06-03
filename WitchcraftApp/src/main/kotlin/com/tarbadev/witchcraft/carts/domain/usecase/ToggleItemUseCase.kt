package com.tarbadev.witchcraft.carts.domain.usecase

import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import com.tarbadev.witchcraft.carts.domain.repository.ItemRepository
import org.springframework.stereotype.Component

@Component
class ToggleItemUseCase(private val cartRepository: CartRepository, private val itemRepository: ItemRepository) {
  fun execute(cartId: Int, itemId: Int, enabled: Boolean): Item {
    val cart = cartRepository.findById(cartId)
    val updatedItem = cart!!.items.find { it.id == itemId }!!
        .copy(enabled = enabled)
    return itemRepository.update(updatedItem)
  }
}
