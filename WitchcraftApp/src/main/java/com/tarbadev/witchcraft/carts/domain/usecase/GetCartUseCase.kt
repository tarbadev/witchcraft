package com.tarbadev.witchcraft.carts.domain.usecase

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.springframework.stereotype.Component

@Component
class GetCartUseCase(private val cartRepository: CartRepository) {

    fun execute(id: Int): Cart? {
        return cartRepository.findById(id)
    }
}
