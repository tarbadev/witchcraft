package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.RecipeCatalogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipesRestController {
  private RecipeCatalogUseCase recipeCatalogUseCase;

  @Autowired
  public RecipesRestController(RecipeCatalogUseCase recipeCatalogUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
  }

  @GetMapping
  public Map<String, List<Recipe>> list() {
    Map<String, List<Recipe>> returnMap = new HashMap<>();
    returnMap.put("recipes", recipeCatalogUseCase.execute());

    return returnMap;
  }
}
