package com.tarbadev.witchcraft.domain;

import java.util.List;

public interface CartRepository {
  List<Cart> findAll();
  Cart save(Cart cart);
  Cart findById(Integer id);
}
