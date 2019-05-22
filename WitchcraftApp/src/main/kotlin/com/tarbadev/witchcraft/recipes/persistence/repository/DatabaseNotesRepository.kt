package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.repository.NotesRepository
import org.springframework.stereotype.Component

@Component
class DatabaseNotesRepository(private val notesEntityRepository: NotesEntityRepository): NotesRepository {
  override fun findByRecipeId(recipeId: Int): Notes? {
    val notesEntity = notesEntityRepository.findByRecipeId(recipeId) ?: return null
    return notesEntity.toNotes()
  }
}