package com.tarbadev.witchcraft.carts.rest.entity

import com.tarbadev.witchcraft.carts.domain.entity.Item
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ItemResponseTest {
  @Test
  fun fromItem() {
    val item = Item(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.0,
        unit = "lb",
        enabled = true
    )

    val itemResponse = ItemResponse(
        id = 10,
        name = "Raclette cheese",
        quantity = 2.0,
        unit = "lb",
        enabled = true
    )

    assertThat(ItemResponse.fromItem(item)).isEqualTo(itemResponse)
  }
}