package com.tarbadev.witchcraft;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseCartRepository {
    private CartRepository cartRepository;

    public DatabaseCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

  public Cart save(Cart cart) {
    return cartRepository.saveAndFlush(cart);
  }
}
