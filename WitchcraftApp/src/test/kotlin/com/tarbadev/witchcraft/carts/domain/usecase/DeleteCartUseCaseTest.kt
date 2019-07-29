package com.tarbadev.witchcraft.carts.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.junit.jupiter.api.Test

class DeleteCartUseCaseTest {
  private val cartRepository: CartRepository = mock()
  private val deleteCartUseCase = DeleteCartUseCase(cartRepository)

  @Test
  fun execute() {
    deleteCartUseCase.execute(2)
    verify(cartRepository).delete(2)
  }
}