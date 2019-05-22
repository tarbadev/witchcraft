package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NotesResponseTest {
  @Test
  fun `constructor initiates object from a Notes`() {
    val recipe = Notes(
        recipeId = 1,
        comment = "Some notes about something"
    )

    val expectedNotesResponse = NotesResponse(
        recipeId = 1,
        notes = "Some notes about something"
    )

    assertThat(NotesResponse.fromNotes(recipe)).isEqualTo(expectedNotesResponse)
  }
}