package com.tarbadev.witchcraft.carts.persistence.repository

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import com.tarbadev.witchcraft.carts.persistence.entity.CartEntity
import org.springframework.stereotype.Repository

@Repository
class DatabaseCartRepository(private val cartEntityRepository: CartEntityRepository) : CartRepository {

    override fun findAll(): List<Cart> {
        return cartEntityRepository.findAll().map { it.toCart() }
    }

    override fun save(cart: Cart): Cart {
        return cartEntityRepository.saveAndFlush(CartEntity.fromCart(cart)).toCart()
    }

    override fun findById(id: Int): Cart? {
        return cartEntityRepository.findById(id)
            .map { it.toCart() }
            .orElse(null)
    }
}
