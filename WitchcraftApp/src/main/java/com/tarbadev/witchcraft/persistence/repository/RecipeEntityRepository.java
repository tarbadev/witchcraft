package com.tarbadev.witchcraft.persistence.repository;

import com.tarbadev.witchcraft.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeEntityRepository extends JpaRepository<RecipeEntity, Integer> {
  List<RecipeEntity> findAllByOrderByName();

  List<RecipeEntity> findAllByFavorite(Boolean favorite);

  List<RecipeEntity> findTop5ByOrderByIdDesc();
}
