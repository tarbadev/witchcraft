package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Notes

data class EditNotesRequest(
    val recipeId: Int = 0,
    val notes: String = ""
) {
  fun toNotes(): Notes {
    return Notes(
        recipeId = recipeId,
        comment = notes
    )
  }
}
