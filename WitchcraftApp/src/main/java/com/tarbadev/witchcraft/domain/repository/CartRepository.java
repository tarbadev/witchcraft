package com.tarbadev.witchcraft.domain.repository;

import com.tarbadev.witchcraft.domain.entity.Cart;

import java.util.List;

public interface CartRepository {
  List<Cart> findAll();
  Cart save(Cart cart);
  Cart findById(Integer id);
}
