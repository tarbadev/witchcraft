package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.repository.NotesRepository
import org.springframework.stereotype.Component

@Component
class EditRecipeNotesUseCase(private val notesRepository: NotesRepository) {
  fun execute(notes: Notes): Notes {
    return notesRepository.save(notes)
  }
}
