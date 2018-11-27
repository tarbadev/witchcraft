package com.tarbadev.witchcraft.carts.rest

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/carts")
class CartsRestController(
    private val recipeCatalogUseCase: RecipeCatalogUseCase,
    private val createCartUseCase: CreateCartUseCase,
    private val cartCatalogUseCase: CartCatalogUseCase,
    private val getCartUseCase: GetCartUseCase
) {

    @GetMapping
    fun all(): List<Cart> = cartCatalogUseCase.execute()

    @GetMapping("/{id}")
    fun getCart(@PathVariable id: Int?): Cart = getCartUseCase.execute(id!!)!!

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createCartRequests: List<CreateCartRequest>): Cart {
        val recipesCatalog = recipeCatalogUseCase.execute()
        val recipes = recipesCatalog
            .filter { recipe -> createCartRequests.any { createCartRequest -> createCartRequest.id == recipe.id } }

        return createCartUseCase.execute(recipes)
    }
}
