package com.tarbadev.witchcraft.recipes.persistence.repository;

import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeEntityRepository extends JpaRepository<RecipeEntity, Integer> {
  List<RecipeEntity> findAllByOrderByName();

  List<RecipeEntity> findAllByFavorite(Boolean favorite);

  List<RecipeEntity> findTop8ByOrderByIdDesc();
}
