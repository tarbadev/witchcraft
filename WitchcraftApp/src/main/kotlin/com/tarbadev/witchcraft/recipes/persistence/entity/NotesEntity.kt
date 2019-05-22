package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "notes")
data class NotesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var recipeId: Int = 0,
    var comment: String = ""
) {
  fun toNotes(): Notes {
    return Notes(id = id, recipeId = recipeId, comment = comment)
  }

  companion object {
    fun fromNotes(notes: Notes): NotesEntity {
      return NotesEntity(
          id = notes.id,
          recipeId = notes.recipeId,
          comment = notes.comment
      )
    }
  }
}
