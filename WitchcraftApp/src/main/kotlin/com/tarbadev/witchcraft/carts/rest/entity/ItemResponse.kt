package com.tarbadev.witchcraft.carts.rest.entity

import com.tarbadev.witchcraft.carts.domain.entity.Item

data class ItemResponse(
    val id: Int = 0,
    val name: String,
    val quantity: Double,
    val unit: String
) {
  companion object {
    fun fromItem(item: Item): ItemResponse {
      return ItemResponse(
          item.id,
          item.name,
          item.quantity,
          item.unit
      )
    }
  }
}