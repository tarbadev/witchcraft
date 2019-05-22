package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EditNotesRequestTest {
  @Test
  fun `toNotes returns a Notes object`() {
    val expectedNotes = Notes(
        recipeId = 15,
        comment = "Some comment"
    )
    val notesRequest = EditNotesRequest(
        recipeId = 15,
        notes = "Some comment"
    )

    assertThat(notesRequest.toNotes()).isEqualTo(expectedNotes)
  }
}