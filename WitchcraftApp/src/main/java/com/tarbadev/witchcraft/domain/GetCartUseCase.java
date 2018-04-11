package com.tarbadev.witchcraft.domain;

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
