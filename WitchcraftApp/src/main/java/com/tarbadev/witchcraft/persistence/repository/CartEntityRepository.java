package com.tarbadev.witchcraft.persistence.repository;

import com.tarbadev.witchcraft.persistence.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntityRepository extends JpaRepository<CartEntity, Integer> {
}
