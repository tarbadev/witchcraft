package com.tarbadev.witchcraft.carts.persistence.repository

import com.tarbadev.witchcraft.carts.persistence.entity.CartEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CartEntityRepository : JpaRepository<CartEntity, Int>
