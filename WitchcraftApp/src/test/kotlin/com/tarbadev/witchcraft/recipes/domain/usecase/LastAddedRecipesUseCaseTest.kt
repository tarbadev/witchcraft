package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LastAddedRecipesUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()
    private lateinit var subject: LastAddedRecipesUseCase

    @BeforeEach
    fun setUp() {
        subject = LastAddedRecipesUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        val recipes = listOf(
            Recipe(),
            Recipe(),
            Recipe(),
            Recipe(),
            Recipe()
        )

        whenever(recipeRepository.findLastAddedRecipes()).thenReturn(recipes)

        assertEquals(recipes, subject.execute())
    }
}