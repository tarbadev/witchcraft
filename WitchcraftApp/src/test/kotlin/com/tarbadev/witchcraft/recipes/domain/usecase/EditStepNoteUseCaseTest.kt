package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import com.tarbadev.witchcraft.recipes.domain.repository.StepRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EditStepNoteUseCaseTest {
  private val stepRepository: StepRepository = mock()
  private val editStepNoteUseCase = EditStepNoteUseCase(stepRepository)

  @Test
  fun execute() {
    val stepId = 15
    val note = "Some Note"
    val step = Step(id = stepId)
    val newStep = step.copy(note = StepNote(comment = note))

    whenever(stepRepository.findById(stepId)).thenReturn(step)
    whenever(stepRepository.save(newStep)).thenReturn(newStep)

    assertThat(editStepNoteUseCase.execute(stepId, note)).isEqualTo(newStep)
  }
}