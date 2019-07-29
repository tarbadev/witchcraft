package com.tarbadev.witchcraft.recipes.domain.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Step

interface StepRepository {
  fun findById(id: Int): Step?
  fun save(step: Step): Step
}