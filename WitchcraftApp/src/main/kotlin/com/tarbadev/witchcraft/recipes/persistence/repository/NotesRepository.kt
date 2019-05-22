package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.persistence.entity.NotesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NotesEntityRepository : JpaRepository<NotesEntity, Int> {
  fun findByRecipeId(recipeId: Int): NotesEntity?
}
