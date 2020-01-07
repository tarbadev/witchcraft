package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.repository.IngredientRepository
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import org.springframework.stereotype.Component

@Component
class DatabaseIngredientRepository(
    private val ingredientEntityRepository: IngredientEntityRepository
) : IngredientRepository {

  override fun save(ingredient: Ingredient): Ingredient {
    return ingredientEntityRepository
        .save(IngredientEntity(ingredient))
        .toIngredient()
  }
}
