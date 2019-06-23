package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StepModifyRequestTest {
  @Test
  fun toStep() {
    val stepModifyRequest = StepModifyRequest(
        id = 23,
        name = "Something to do"
    )

    val step = Step(
        id = 23,
        name = "Something to do"
    )

    assertThat(stepModifyRequest.toStep()).isEqualTo(step)
  }
}