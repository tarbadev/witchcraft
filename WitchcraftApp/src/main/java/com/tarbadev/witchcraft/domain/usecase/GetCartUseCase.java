package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
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
