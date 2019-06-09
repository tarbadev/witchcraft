package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify

class SaveRecipeUseCaseTest {
    private lateinit var saveRecipeUseCase: SaveRecipeUseCase
    private val recipeRepository: RecipeRepository = mock()

    @BeforeEach
    fun setUp() {
        saveRecipeUseCase = SaveRecipeUseCase(recipeRepository)
        reset(recipeRepository)
    }

    @Test
    fun execute() {
        val recipe = Recipe()

        saveRecipeUseCase.execute(recipe)

        verify<RecipeRepository>(recipeRepository).save(recipe)
    }

    @Test
    fun execute_updatesRecipe() {
        val recipe = Recipe(id = 123)

        saveRecipeUseCase.execute(recipe)

        verify<RecipeRepository>(recipeRepository).update(recipe)
    }
}