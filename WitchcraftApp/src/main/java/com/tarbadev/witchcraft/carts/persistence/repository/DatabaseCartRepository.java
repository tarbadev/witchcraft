package com.tarbadev.witchcraft.carts.persistence.repository;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.carts.domain.entity.Item;
import com.tarbadev.witchcraft.carts.domain.repository.CartRepository;
import com.tarbadev.witchcraft.carts.persistence.entity.CartEntity;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatabaseCartRepository implements CartRepository {
  private final CartEntityRepository cartEntityRepository;

  public DatabaseCartRepository(CartEntityRepository cartEntityRepository) {
    this.cartEntityRepository = cartEntityRepository;
  }

  @Override
  public List<Cart> findAll() {
    return cartEntityRepository.findAll().stream()
        .map(CartEntity::cart)
        .collect(Collectors.toList());
  }

  @Override
  public Cart save(Cart cart) {
    return cartEntityRepository.saveAndFlush(new CartEntity(cart)).cart();
  }

  @Override
  public Cart findById(Integer id) {
    Cart cart = cartEntityRepository.findById(id).map(CartEntity::cart).orElse(null);
    cart.getItems().sort(Comparator.comparing(Item::getName));
    return cart;
  }
}
