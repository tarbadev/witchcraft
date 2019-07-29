package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.repository.StepRepository
import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity
import org.springframework.stereotype.Component

@Component
class DatabaseStepRepository(private val stepEntityRepository: StepEntityRepository) : StepRepository {
  override fun findById(id: Int): Step? {
    return stepEntityRepository.findById(id).map { it.toStep() }.orElse(null)
  }

  override fun save(step: Step): Step {
    return stepEntityRepository.save(StepEntity(step)).toStep()
  }
}