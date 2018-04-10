package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Cart;
import com.tarbadev.witchcraft.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class DatabaseCartRepository {
  private CartEntityRepository cartEntityRepository;

  public DatabaseCartRepository(CartEntityRepository cartEntityRepository) {
    this.cartEntityRepository = cartEntityRepository;
  }

  public List<Cart> findAll() {
    return cartEntityRepository.findAll();
  }

  public Cart save(Cart cart) {
    return cartEntityRepository.saveAndFlush(cart);
  }

  public Cart findById(Integer id) {
    Cart cart = cartEntityRepository.findById(id).get();
    cart.getItems().sort(Comparator.comparing(Item::getName));
    return cart;
  }
}
