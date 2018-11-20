package com.tarbadev.witchcraft.persistence.repository;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
import com.tarbadev.witchcraft.domain.entity.Item;
import com.tarbadev.witchcraft.persistence.helpers.EntityToDomain;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tarbadev.witchcraft.persistence.helpers.DomainToEntity.cartEntityMapper;
import static com.tarbadev.witchcraft.persistence.helpers.EntityToDomain.cartMapper;

@Repository
public class DatabaseCartRepository implements CartRepository {
  private final CartEntityRepository cartEntityRepository;

  public DatabaseCartRepository(CartEntityRepository cartEntityRepository) {
    this.cartEntityRepository = cartEntityRepository;
  }

  @Override
  public List<Cart> findAll() {
    return cartEntityRepository.findAll().stream()
        .map(EntityToDomain::cartMapper)
        .collect(Collectors.toList());
  }

  @Override
  public Cart save(Cart cart) {
    return cartMapper(cartEntityRepository.saveAndFlush(cartEntityMapper(cart)));
  }

  @Override
  public Cart findById(Integer id) {
    Cart cart = cartEntityRepository.findById(id).map(EntityToDomain::cartMapper).orElse(null);
    cart.getItems().sort(Comparator.comparing(Item::getName));
    return cart;
  }
}
