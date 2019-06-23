package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step

data class StepModifyRequest(
    var id: Int = 0,
    var name: String = ""
) {
  fun toStep(): Step {
    return Step(id, name)
  }
}
