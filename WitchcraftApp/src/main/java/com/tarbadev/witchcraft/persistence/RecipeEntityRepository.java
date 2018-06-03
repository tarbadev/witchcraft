package com.tarbadev.witchcraft.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeEntityRepository extends JpaRepository<RecipeEntity, Integer> {
  List<RecipeEntity> findAllByOrderByName();

  List<RecipeEntity> findTop5ByOrderByRatingDesc();

  List<RecipeEntity> findTop5ByOrderByIdDesc();
}
