package com.tarbadev.witchcraft.learning.persistence.repository

import com.tarbadev.witchcraft.learning.persistence.entity.LearningIngredientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LearningIngredientEntityRepository : JpaRepository<LearningIngredientEntity, Int>
