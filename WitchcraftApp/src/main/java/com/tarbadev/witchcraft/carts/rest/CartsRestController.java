package com.tarbadev.witchcraft.carts.rest;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase;
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartsRestController {
  private final RecipeCatalogUseCase recipeCatalogUseCase;
  private final CreateCartUseCase createCartUseCase;
  private final CartCatalogUseCase cartCatalogUseCase;
  private final GetCartUseCase getCartUseCase;

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
