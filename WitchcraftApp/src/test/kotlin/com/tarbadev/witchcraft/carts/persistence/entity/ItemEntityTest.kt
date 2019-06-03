package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Item
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ItemEntityTest {
  @Test
  fun fromItem() {
    val item = Item(
        id = 12,
        name = "Some item",
        quantity = 2.0,
        unit = "lb",
        enabled = true
    )

    val itemEntity = ItemEntity(
        id = 12,
        name = "Some item",
        quantity = 2.0,
        unit = "lb",
        enabled = true
    )

    assertThat(ItemEntity.fromItem(item)).isEqualTo(itemEntity)
  }
}