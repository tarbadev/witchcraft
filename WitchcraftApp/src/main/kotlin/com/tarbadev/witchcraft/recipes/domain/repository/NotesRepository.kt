package com.tarbadev.witchcraft.recipes.domain.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Notes

interface NotesRepository {
  fun findByRecipeId(recipeId: Int): Notes?
}
