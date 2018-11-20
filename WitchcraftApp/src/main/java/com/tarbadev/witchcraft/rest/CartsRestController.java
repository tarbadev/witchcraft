package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.CartCatalogUseCase;
import com.tarbadev.witchcraft.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.domain.usecase.GetCartUseCase;
import com.tarbadev.witchcraft.domain.usecase.RecipeCatalogUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartsRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private CreateCartUseCase createCartUseCase;
  private CartCatalogUseCase cartCatalogUseCase;
  private GetCartUseCase getCartUseCase;

  public CartsRestController(
      RecipeCatalogUseCase recipeCatalogUseCase,
      CreateCartUseCase createCartUseCase,
      CartCatalogUseCase cartCatalogUseCase,
      GetCartUseCase getCartUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.createCartUseCase = createCartUseCase;
    this.cartCatalogUseCase = cartCatalogUseCase;
    this.getCartUseCase = getCartUseCase;
  }

  @GetMapping("/{id}")
  public Cart getCart(@PathVariable Integer id) {
    return getCartUseCase.execute(id);
  }

  @GetMapping
  public List<Cart> getAll() {
    return cartCatalogUseCase.execute();
  }

  @PostMapping
  public Cart create(@RequestBody List<CreateCartRequest> createCartRequests) {
    List<Recipe> recipesCatalog = recipeCatalogUseCase.execute();
    List<Recipe> recipes = recipesCatalog.stream()
        .filter(recipe -> createCartRequests.stream().anyMatch(createCartRequest -> createCartRequest.getId().equals(recipe.getId())))
        .collect(Collectors.toList());

    return createCartUseCase.execute(recipes);
  }
}
