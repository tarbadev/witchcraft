package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientEntityRepository : JpaRepository<IngredientEntity, Int>