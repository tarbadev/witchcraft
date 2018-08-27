package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
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
