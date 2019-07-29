package com.tarbadev.witchcraft.carts.domain.repository

import com.tarbadev.witchcraft.carts.domain.entity.Cart

interface CartRepository {
    fun findAll(): List<Cart>
    fun save(cart: Cart): Cart
    fun findById(id: Int): Cart?
    fun delete(id: Int)
}
