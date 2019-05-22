package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NotesEntityTest {
  @Test
  fun `toNotes returns a Notes object`() {
    val notesEntity = NotesEntity(
        id = 45,
        recipeId = 10,
        comment = "Raclette cheese"
    )

    val expectedNotes = Notes(
        recipeId = 10,
        comment = "Raclette cheese"
    )

    assertThat(notesEntity.toNotes()).isEqualTo(expectedNotes)
  }
}