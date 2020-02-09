package com.tarbadev.witchcraft.carts.rest

import com.tarbadev.witchcraft.carts.domain.usecase.*
import com.tarbadev.witchcraft.carts.rest.entity.CartResponse
import com.tarbadev.witchcraft.carts.rest.entity.CreateCartRequest
import com.tarbadev.witchcraft.carts.rest.entity.ItemResponse
import com.tarbadev.witchcraft.carts.rest.entity.UpdateItemRequest
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/carts")
class CartsRestController(
    private val recipeCatalogUseCase: RecipeCatalogUseCase,
    private val createCartUseCase: CreateCartUseCase,
    private val cartCatalogUseCase: CartCatalogUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val toggleItemUseCase: ToggleItemUseCase,
    private val deleteCartUseCase: DeleteCartUseCase
) {

  @GetMapping
  fun all(): List<CartResponse> = cartCatalogUseCase.execute().map { CartResponse.fromCart(it) }

  @GetMapping("/{id}")
  fun getCart(@PathVariable id: Int): CartResponse = CartResponse.fromCart(getCartUseCase.execute(id)!!)

  @PostMapping
  fun create(@RequestBody createCartRequests: List<CreateCartRequest>): ResponseEntity<CartResponse> {
    val recipesCatalog = recipeCatalogUseCase.execute()
    return try {
      val recipes = createCartRequests
          .map { recipesCatalog.first { recipe -> it.id == recipe.id } }

      ResponseEntity.status(CREATED).body(CartResponse.fromCart(createCartUseCase.execute(recipes)))
    } catch(exception: NoSuchElementException) {
      val logger = LoggerFactory.getLogger(CartsRestController::class.java)
      logger.error("Request: [$createCartRequests]")
      logger.error("Catalog: [$recipesCatalog]")
      logger.error("RECIPE NOT FOUND", exception)

      ResponseEntity(BAD_REQUEST)
    }
  }

  @PutMapping("/{cartId}/items/{itemId}")
  fun updateItem(
      @PathVariable cartId: Int,
      @PathVariable itemId: Int,
      @RequestBody updateItemRequest: UpdateItemRequest
  ): ItemResponse {
    val item = toggleItemUseCase.execute(cartId, itemId, updateItemRequest.enabled)
    return ItemResponse.fromItem(item)
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteCart(@PathVariable id: Int) {
    deleteCartUseCase.execute(id)
  }
}
