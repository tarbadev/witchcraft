package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByOrderByName();
}
