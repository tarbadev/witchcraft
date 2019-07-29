package com.tarbadev.witchcraft.carts.domain.usecase

import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.springframework.stereotype.Component

@Component
class DeleteCartUseCase(private val cartRepository: CartRepository) {
  fun execute(id: Int) {
    cartRepository.delete(id)
  }
}