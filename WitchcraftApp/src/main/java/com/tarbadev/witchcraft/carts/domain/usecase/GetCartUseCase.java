package com.tarbadev.witchcraft.carts.domain.usecase;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository;
import org.springframework.stereotype.Component;

@Component
public class GetCartUseCase {
  private final CartRepository cartRepository;

  public GetCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Cart execute(int id) {
    return cartRepository.findById(id);
  }
}
