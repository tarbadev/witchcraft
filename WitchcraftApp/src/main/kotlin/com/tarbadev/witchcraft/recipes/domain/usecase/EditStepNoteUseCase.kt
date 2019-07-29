package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import com.tarbadev.witchcraft.recipes.domain.repository.StepRepository
import org.springframework.stereotype.Component

@Component
class EditStepNoteUseCase(private val stepRepository: StepRepository) {
  fun execute(stepId: Int, note: String): Step {
    val step = stepRepository.findById(stepId)
    val modifiedStep = step!!.copy(note = StepNote(comment = note))

    return stepRepository.save(modifiedStep)
  }
}