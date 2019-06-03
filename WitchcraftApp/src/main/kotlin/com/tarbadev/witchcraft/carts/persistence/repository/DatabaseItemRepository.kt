package com.tarbadev.witchcraft.carts.persistence.repository

import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.carts.domain.repository.ItemRepository
import com.tarbadev.witchcraft.carts.persistence.entity.ItemEntity
import org.springframework.stereotype.Component

@Component
class DatabaseItemRepository(private val itemEntityRepository: ItemEntityRepository) : ItemRepository {
  override fun update(item: Item): Item {
    return itemEntityRepository.save(ItemEntity.fromItem(item)).toItem()
  }
}