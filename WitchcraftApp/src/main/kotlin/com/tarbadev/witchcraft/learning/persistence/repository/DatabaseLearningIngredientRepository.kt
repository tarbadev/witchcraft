package com.tarbadev.witchcraft.learning.persistence.repository

import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import org.springframework.stereotype.Repository

@Repository
class DatabaseLearningIngredientRepository(
    private val learningIngredientEntityRepository: LearningIngredientEntityRepository
) : LearningIngredientRepository {
  override fun findAll(): List<LearningIngredient> {
    return learningIngredientEntityRepository.findAll()
        .map { it.toLearningIngredient() }
  }
}