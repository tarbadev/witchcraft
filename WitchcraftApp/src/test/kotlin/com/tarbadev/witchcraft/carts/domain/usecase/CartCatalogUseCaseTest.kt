package com.tarbadev.witchcraft.carts.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.Arrays

class CartCatalogUseCaseTest {
    private val cartRepository: CartRepository = mock()
    private var cartCatalogUseCase: CartCatalogUseCase = CartCatalogUseCase(cartRepository)

    @BeforeEach
    fun setUp() {
        reset(cartRepository)
    }

    @Test
    fun execute() {
        val carts = Arrays.asList(
                Cart(),
                Cart(),
                Cart(),
                Cart()
        )

        whenever(cartRepository.findAll()).thenReturn(carts)

        assertEquals(carts, cartCatalogUseCase.execute())
    }
}