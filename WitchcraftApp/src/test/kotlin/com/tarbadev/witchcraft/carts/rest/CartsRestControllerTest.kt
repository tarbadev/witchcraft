package com.tarbadev.witchcraft.carts.rest

import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase
import com.tarbadev.witchcraft.carts.rest.entity.CartResponse
import com.tarbadev.witchcraft.carts.rest.entity.CreateCartRequest
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Arrays.asList

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartsRestControllerTest(
    @Autowired private val testRestTemplate: TestRestTemplate
) {
    @MockBean private lateinit var getCartUseCase: GetCartUseCase
    @MockBean private lateinit var recipeCatalogUseCase: RecipeCatalogUseCase
    @MockBean private lateinit var createCartUseCase: CreateCartUseCase
    @MockBean private lateinit var cartCatalogUseCase: CartCatalogUseCase

    @Test
    fun getCart() {
        val cart = Cart(id = 12)

        whenever(getCartUseCase.execute(cart.id)).thenReturn(cart)

        val responseEntity = testRestTemplate.getForEntity("/api/carts/${cart.id}", CartResponse::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        assertEquals(CartResponse.fromCart(cart), responseEntity.body)
    }

    @Test
    fun getAll() {
        val carts = asList(
            Cart(),
            Cart(),
            Cart()
        )

        whenever(cartCatalogUseCase.execute()).thenReturn(carts)

        val responseEntity = testRestTemplate.getForEntity("/api/carts", Array<CartResponse>::class.java)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        assertEquals(carts.map { CartResponse.fromCart(it) }, responseEntity.body!!.toList())
    }

    @Test
    fun create() {
        val createCartRequests = asList(
            CreateCartRequest(id = 1),
            CreateCartRequest(id = 2),
            CreateCartRequest(id = 4)
        )

        val recipes = asList(
            Recipe(id = 1),
            Recipe(id = 2),
            Recipe(id = 3),
            Recipe(id = 4),
            Recipe(id = 5)
        )
        val cartRecipes = recipes
            .filter { recipe ->
                createCartRequests.any { createCartRequest -> createCartRequest.id == recipe.id }
            }
        val cart = Cart(recipes = recipes)

        whenever(recipeCatalogUseCase.execute()).thenReturn(recipes)
        whenever(createCartUseCase.execute(cartRecipes)).thenReturn(cart)

        val responseEntity = testRestTemplate.postForEntity("/api/carts", createCartRequests, CartResponse::class.java)

        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertEquals(APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        assertEquals(CartResponse.fromCart(cart), responseEntity.body)
    }
}