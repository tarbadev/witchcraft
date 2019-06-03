package com.tarbadev.witchcraft.carts.persistence.repository

import com.tarbadev.witchcraft.carts.persistence.entity.ItemEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseItemRepositoryTest(
    @Autowired private val entityManager: TestEntityManager,
    @Autowired private val itemEntityRepository: ItemEntityRepository
) {
  private var databaseItemRepository: DatabaseItemRepository = DatabaseItemRepository(itemEntityRepository)

  @Test
  fun update() {
    val itemEntity = entityManager.persistAndFlush(ItemEntity(enabled = true))
    val item = itemEntity.toItem().copy(enabled = false)
    val expectedItemEntity = ItemEntity.fromItem(item)

    entityManager.clear()

    val updatedItem = databaseItemRepository.update(item)

    assertThat(entityManager.find(ItemEntity::class.java, updatedItem.id)).isEqualTo(expectedItemEntity)
  }
}