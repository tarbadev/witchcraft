package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step

data class StepResponse(
    val id: Int,
    val name: String
) {
  companion object {
    fun fromStep(step: Step): StepResponse =
        StepResponse(
            id = step.id,
            name = step.name
        )
  }
}
