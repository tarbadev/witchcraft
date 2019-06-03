package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Item
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "item")
data class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = "",
    val enabled: Boolean = true
) {
  companion object {
    fun fromItem(item: Item): ItemEntity {
      return ItemEntity(
          item.id,
          item.name,
          item.quantity,
          item.unit,
          item.enabled
      )
    }
  }

  fun toItem(): Item {
    return Item(
        id = id,
        name = name,
        quantity = quantity,
        unit = unit,
        enabled = enabled
    )
  }
}
