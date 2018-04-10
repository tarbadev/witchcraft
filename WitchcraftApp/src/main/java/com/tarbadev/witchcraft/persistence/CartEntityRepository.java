package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntityRepository extends JpaRepository<Cart, Integer> {
}
