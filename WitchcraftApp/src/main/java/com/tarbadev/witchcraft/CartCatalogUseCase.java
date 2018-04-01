package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartCatalogUseCase {
    private DatabaseCartRepository databaseCartRepository;

    public CartCatalogUseCase(DatabaseCartRepository databaseCartRepository) {
        this.databaseCartRepository = databaseCartRepository;
    }

    public List<Cart> execute() {
        return databaseCartRepository.findAll();
    }
}
