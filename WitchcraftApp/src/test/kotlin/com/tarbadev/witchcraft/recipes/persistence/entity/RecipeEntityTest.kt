package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipeEntityTest {
  @Test
  fun toRecipe() {
    val ingredientEntity = IngredientEntity(
        id = 1,
        name = "some toIngredient",
        quantity = 2.0,
        unit = "cup"
    )
    val stepEntity = StepEntity(
        id = 65,
        name = "Some Step",
        notes = listOf(StepNoteEntity(
            id = 34,
            comment = "Some step comment"
        ))
    )

    val recipeEntity = RecipeEntity(
        id = 34,
        originUrl = "Some url",
        name = "Dummy recipe",
        imgUrl = "Some Url",
        favorite = true,
        isArchived = true,
        ingredients = listOf(ingredientEntity),
        steps = setOf(stepEntity),
        portions = 34
    )
    val recipe = Recipe(
        id = 34,
        originUrl = "Some url",
        name = "dummy recipe",
        imgUrl = "Some Url",
        favorite = true,
        isArchived = true,
        ingredients = listOf(ingredientEntity.toIngredient()),
        steps = listOf(stepEntity.toStep()),
        portions = 34
    )

    assertThat(recipeEntity.toRecipe()).isEqualTo(recipe)
  }
  @Test
  fun fromRecipe() {
    val ingredientEntity = IngredientEntity(
        id = 1,
        name = "some toIngredient",
        quantity = 2.0,
        unit = "cup"
    )
    val stepEntity = StepEntity(
        id = 65,
        name = "Some Step",
        notes = listOf(StepNoteEntity(
            id = 34,
            comment = "Some step comment"
        ))
    )

    val recipeEntity = RecipeEntity(
        id = 34,
        originUrl = "Some url",
        name = "Dummy Recipe",
        imgUrl = "Some Url",
        favorite = true,
        isArchived = true,
        ingredients = listOf(ingredientEntity),
        steps = setOf(stepEntity),
        portions = 34
    )
    val recipe = Recipe(
        id = 34,
        originUrl = "Some url",
        name = "Dummy Recipe",
        imgUrl = "Some Url",
        favorite = true,
        isArchived = true,
        ingredients = listOf(ingredientEntity.toIngredient()),
        steps = listOf(stepEntity.toStep()),
        portions = 34
    )

    assertThat(RecipeEntity.fromRecipe(recipe)).isEqualTo(recipeEntity)
  }
}