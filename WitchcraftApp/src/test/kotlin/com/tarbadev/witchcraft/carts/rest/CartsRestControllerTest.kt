package com.tarbadev.witchcraft.carts.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase
import com.tarbadev.witchcraft.carts.rest.entity.CartResponse
import com.tarbadev.witchcraft.carts.rest.entity.CreateCartRequest
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase
import com.tarbadev.witchcraft.recipes.rest.RecipesRestController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.Arrays.asList

@ExtendWith(SpringExtension::class)
@WebMvcTest(CartsRestController::class)
class CartsRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
  @MockBean
  private lateinit var getCartUseCase: GetCartUseCase
  @MockBean
  private lateinit var recipeCatalogUseCase: RecipeCatalogUseCase
  @MockBean
  private lateinit var createCartUseCase: CreateCartUseCase
  @MockBean
  private lateinit var cartCatalogUseCase: CartCatalogUseCase

  @Test
  fun getCart() {
    val cart = Cart(id = 12)

    whenever(getCartUseCase.execute(cart.id)).thenReturn(cart)

    mockMvc.perform(get("/api/carts/${cart.id}"))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(jacksonObjectMapper().writeValueAsString(CartResponse.fromCart(cart))))
  }

  @Test
  fun getAll() {
    val carts = asList(
        Cart(),
        Cart(),
        Cart()
    )

    whenever(cartCatalogUseCase.execute()).thenReturn(carts)

    mockMvc.perform(get("/api/carts"))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(jacksonObjectMapper().writeValueAsString(carts.map { CartResponse.fromCart(it) })))
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

    mockMvc.perform(post("/api/carts")
        .contentType(APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(createCartRequests))
    )
        .andExpect(MockMvcResultMatchers.status().isCreated)
        .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(jacksonObjectMapper().writeValueAsString(CartResponse.fromCart(cart))))
  }
}