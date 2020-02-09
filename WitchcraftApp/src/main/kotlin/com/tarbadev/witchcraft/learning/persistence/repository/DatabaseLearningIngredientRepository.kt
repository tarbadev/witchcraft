package com.tarbadev.witchcraft.learning.persistence.repository

import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.repository.LearningIngredientRepository
import com.tarbadev.witchcraft.learning.persistence.entity.LearningIngredientEntity
import org.springframework.stereotype.Repository

@Repository
class DatabaseLearningIngredientRepository(
    private val learningIngredientEntityRepository: LearningIngredientEntityRepository
) : LearningIngredientRepository {

  override fun findAll(): List<LearningIngredient> {
    return learningIngredientEntityRepository.findAll()
        .map { it.toLearningIngredient() }
  }

  override fun findById(id: Int): LearningIngredient? {
    return learningIngredientEntityRepository.findById(id).map { it.toLearningIngredient() }.orElse(null)
  }

  override fun save(learningIngredient: LearningIngredient): LearningIngredient {
    return learningIngredientEntityRepository.save(LearningIngredientEntity.fromLearningIngredient(learningIngredient))
        .toLearningIngredient()
  }
}