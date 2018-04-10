package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {
  private CartCatalogUseCase cartCatalogUseCase;
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private CreateCartUseCase createCartUseCase;
  private GetCartUseCase getCartUseCase;

  public CartController(CartCatalogUseCase cartCatalogUseCase, RecipeCatalogUseCase recipeCatalogUseCase, CreateCartUseCase createCartUseCase, GetCartUseCase getCartUseCase) {
    this.cartCatalogUseCase = cartCatalogUseCase;
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.createCartUseCase = createCartUseCase;
    this.getCartUseCase = getCartUseCase;
  }

  @GetMapping("/carts")
  public String index(Model model) {
    model.addAttribute("carts", cartCatalogUseCase.execute());

    return "carts/index";
  }

  @GetMapping("/carts/new")
  public String showNewCart(Model model) {
    List<Recipe> recipes = recipeCatalogUseCase.execute();
    NewCartForm newCartForm = new NewCartForm(Collections.emptyList());
    model.addAttribute("newCartForm", newCartForm);
    model.addAttribute("recipes", recipes);
    return "carts/newCart";
  }

  @PostMapping("/carts/new")
  public String newCart(@ModelAttribute NewCartForm newCartForm) {
    List<Recipe> recipesCatalog = recipeCatalogUseCase.execute();
    List<Recipe> recipes = recipesCatalog.stream()
        .filter(recipe -> newCartForm.getCheckedRecipeIds().contains(recipe.getId()))
        .collect(Collectors.toList());

    createCartUseCase.execute(recipes);

    return "redirect:/carts";
  }

  @GetMapping("/carts/{id}")
  public String show(@PathVariable Integer id, Model model) {
    Cart cart = getCartUseCase.execute(id);
    model.addAttribute("cart", cart);
    return "carts/show";
  }
}
