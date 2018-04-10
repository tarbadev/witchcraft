package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.DatabaseCartRepository;
import org.springframework.stereotype.Component;

@Component
public class GetCartUseCase {
  private final DatabaseCartRepository databaseCartRepository;

  public GetCartUseCase(DatabaseCartRepository databaseCartRepository) {
    this.databaseCartRepository = databaseCartRepository;
  }

  public Cart execute(int id) {
    return databaseCartRepository.findById(id);
  }
}
