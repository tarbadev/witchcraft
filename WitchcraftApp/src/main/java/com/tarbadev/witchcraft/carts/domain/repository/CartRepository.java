package com.tarbadev.witchcraft.carts.domain.repository;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;

import java.util.List;

public interface CartRepository {
  List<Cart> findAll();
  Cart save(Cart cart);
  Cart findById(Integer id);
}
