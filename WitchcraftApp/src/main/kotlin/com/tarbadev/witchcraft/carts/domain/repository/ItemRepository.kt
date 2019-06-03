package com.tarbadev.witchcraft.carts.domain.repository

import com.tarbadev.witchcraft.carts.domain.entity.Item

interface ItemRepository {
  fun update(item: Item): Item
}
