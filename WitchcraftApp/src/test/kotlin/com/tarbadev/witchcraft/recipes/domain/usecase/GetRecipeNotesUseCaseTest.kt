package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.repository.NotesRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecipeNotesUseCaseTest {
  private val notesRepository: NotesRepository = mock()
  private val getRecipeNotesUseCase = GetRecipeNotesUseCase(notesRepository)

  @BeforeEach
  fun setUp() {
    reset(notesRepository)
  }

  @Test
  fun execute() {
    val notes = Notes(
        recipeId = 15,
        comment = "Some notes"
    )

    whenever(notesRepository.findByRecipeId(15)).thenReturn(notes)

    assertThat(getRecipeNotesUseCase.execute(15)).isEqualTo(notes)
  }
}