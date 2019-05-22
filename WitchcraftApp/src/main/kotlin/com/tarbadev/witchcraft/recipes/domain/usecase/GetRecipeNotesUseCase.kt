package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.repository.NotesRepository
import org.springframework.stereotype.Component

@Component
class GetRecipeNotesUseCase(private val notesRepository: NotesRepository) {
  fun execute(recipeId: Int): Notes? {
    return notesRepository.findByRecipeId(recipeId)
  }
}
