package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StepResponseTest {
  @Test
  fun `constructor initiates object from a Step`() {
    val step = Step(
        id = 20,
        name = "Make the cheese melt and east, it's good!"
    )

    val expectedStepResponse = StepResponse(
        id = 20,
        name = "Make the cheese melt and east, it's good!"
    )

    assertEquals(expectedStepResponse, StepResponse.fromStep(step))
  }
}