package com.tarbadev.witchcraft.carts.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.nhaarman.mockitokotlin2.whenever

class GetCartUseCaseTest {
    private val cartRepository: CartRepository = mock()
    private val getCartUseCase: GetCartUseCase = GetCartUseCase(cartRepository)

    @BeforeEach
    fun setup() {
        reset(cartRepository)
    }

    @Test
    fun execute() {
        val cart = Cart(id = 123)

        whenever(cartRepository.findById(123)).thenReturn(cart)

        assertEquals(cart, getCartUseCase.execute(123))
    }
}