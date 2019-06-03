package com.tarbadev.witchcraft.carts.persistence.repository

import com.tarbadev.witchcraft.carts.persistence.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemEntityRepository : JpaRepository<ItemEntity, Int>
