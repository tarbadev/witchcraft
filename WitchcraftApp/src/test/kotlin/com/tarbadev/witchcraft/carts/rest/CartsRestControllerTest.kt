package com.tarbadev.witchcraft.carts.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.carts.domain.usecase.*
import com.tarbadev.witchcraft.carts.rest.entity.CartResponse
import com.tarbadev.witchcraft.carts.rest.entity.CreateCartRequest
import com.tarbadev.witchcraft.carts.rest.entity.ItemResponse
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
  @MockBean
  private lateinit var toggleItemUseCase: ToggleItemUseCase
  @MockBean
  private lateinit var deleteCartUseCase: DeleteCartUseCase

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
    val carts = listOf(
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
    val createCartRequests = listOf(
        CreateCartRequest(id = 1),
        CreateCartRequest(id = 4),
        CreateCartRequest(id = 1)
    )

    val recipes = listOf(
        Recipe(id = 1),
        Recipe(id = 2),
        Recipe(id = 3),
        Recipe(id = 4),
        Recipe(id = 5)
    )
    val cartRecipes = listOf(Recipe(id = 1), Recipe(id = 4), Recipe(id = 1))
    val cart = Cart(recipes = recipes)

    whenever(recipeCatalogUseCase.execute()).thenReturn(recipes)
    whenever(createCartUseCase.execute(any())).thenReturn(cart)

    mockMvc.perform(post("/api/carts")
        .contentType(APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(createCartRequests))
    )
        .andExpect(status().isCreated)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(CartResponse.fromCart(cart))))

    verify(recipeCatalogUseCase).execute()
    verify(createCartUseCase).execute(cartRecipes)
  }

  @Test
  fun `create - when recipe does not exist`() {
    val createCartRequests = listOf(
        CreateCartRequest(id = 1),
        CreateCartRequest(id = 4),
        CreateCartRequest(id = 12)
    )

    val recipes = listOf(
        Recipe(id = 1),
        Recipe(id = 2),
        Recipe(id = 3),
        Recipe(id = 4),
        Recipe(id = 5)
    )
    val cart = Cart(recipes = recipes)

    whenever(recipeCatalogUseCase.execute()).thenReturn(recipes)
    whenever(createCartUseCase.execute(any())).thenReturn(cart)

    mockMvc.perform(post("/api/carts")
        .contentType(APPLICATION_JSON_UTF8)
        .content(jacksonObjectMapper().writeValueAsString(createCartRequests))
    )
        .andExpect(status().isBadRequest)

    verify(recipeCatalogUseCase).execute()
    verifyZeroInteractions(createCartUseCase)
  }

  @Test
  fun updateItem() {
    val item = Item(
        id = 43,
        enabled = true,
        name = "Some item",
        quantity = 2.0,
        unit = "lb"
    )
    val itemResponse = ItemResponse.fromItem(item)

    whenever(toggleItemUseCase.execute(2, 43, true)).thenReturn(item)

    mockMvc.perform(put("/api/carts/2/items/43")
        .contentType(APPLICATION_JSON_UTF8)
        .content("{ \"enabled\": true}")
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(itemResponse)))
  }

  @Test
  fun deleteCart() {
    mockMvc.perform(delete("/api/carts/2")).andExpect(status().isNoContent)

    verify(deleteCartUseCase).execute(2)
  }
}