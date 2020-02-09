package com.tarbadev.witchcraft.learning.domain.repository

import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient

interface LearningIngredientRepository {
  fun findAll(): List<LearningIngredient>
  fun findById(id: Int): LearningIngredient?
  fun save(learningIngredient: LearningIngredient): LearningIngredient
}
