package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeEntityRepository : JpaRepository<RecipeEntity, Int> {
    fun findAllByOrderByName(): List<RecipeEntity>

    fun findAllByFavorite(favorite: Boolean?): List<RecipeEntity>

    fun findTop8ByOrderByIdDesc(): List<RecipeEntity>
}
