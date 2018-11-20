package com.tarbadev.witchcraft.carts.domain.usecase;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartCatalogUseCase {
    private final CartRepository cartRepository;

    public CartCatalogUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> execute() {
        return cartRepository.findAll();
    }
}
