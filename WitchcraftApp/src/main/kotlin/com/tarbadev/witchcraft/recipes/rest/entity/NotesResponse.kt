package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes

data class NotesResponse(val recipeId: Int, val notes: String) {
  companion object {
    fun fromNotes(notes: Notes): NotesResponse = NotesResponse(notes.recipeId, notes.comment)
  }
}