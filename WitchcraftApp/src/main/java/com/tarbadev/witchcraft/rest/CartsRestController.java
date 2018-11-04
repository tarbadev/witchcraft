package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.domain.usecase.RecipeCatalogUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartsRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private CreateCartUseCase createCartUseCase;

  public CartsRestController(RecipeCatalogUseCase recipeCatalogUseCase, CreateCartUseCase createCartUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.createCartUseCase = createCartUseCase;
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
