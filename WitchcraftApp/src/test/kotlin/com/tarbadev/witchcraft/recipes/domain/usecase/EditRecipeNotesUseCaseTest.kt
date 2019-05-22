package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.repository.NotesRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EditRecipeNotesUseCaseTest {
  private val notesRepository: NotesRepository = mock()
  private val editRecipeNotesUseCase = EditRecipeNotesUseCase(notesRepository)

  @Test
  fun execute() {
    val notes = Notes(
        recipeId = 15,
        comment = "Some new comment"
    )

    whenever(notesRepository.save(notes)).thenReturn(notes)

    assertThat(editRecipeNotesUseCase.execute(notes)).isEqualTo(notes)
  }
}