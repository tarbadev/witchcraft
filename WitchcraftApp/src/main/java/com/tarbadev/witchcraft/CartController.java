package com.tarbadev.witchcraft;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private CartCatalogUseCase cartCatalogUseCase;

    public CartController(CartCatalogUseCase cartCatalogUseCase) {
        this.cartCatalogUseCase = cartCatalogUseCase;
    }

    @GetMapping("/carts")
    public String index(Model model) {
        model.addAttribute("carts", cartCatalogUseCase.execute());

        return "carts/index";
    }
}
