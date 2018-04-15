package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartCatalogUseCase {
    private CartRepository cartRepository;

    public CartCatalogUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> execute() {
        return cartRepository.findAll();
    }
}
