package com.tarbadev.witchcraft.carts.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import com.tarbadev.witchcraft.carts.domain.repository.ItemRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ToggleItemUseCaseTest {
  private val cartRepository: CartRepository = mock()
  private val itemRepository: ItemRepository = mock()

  private val toggleItemUseCase = ToggleItemUseCase(cartRepository, itemRepository)

  @Test
  fun execute() {
    val item = TestResources.Items.itemLardon.copy(enabled = false)
    val updatedItem = item.copy(enabled = true)
    val cart = TestResources.cart.copy(items = listOf(item))

    whenever(cartRepository.findById(cart.id)).thenReturn(cart)
    whenever(itemRepository.update(updatedItem)).thenReturn(updatedItem)

    assertThat(toggleItemUseCase.execute(cart.id, item.id, true)).isEqualTo(updatedItem)
  }
}