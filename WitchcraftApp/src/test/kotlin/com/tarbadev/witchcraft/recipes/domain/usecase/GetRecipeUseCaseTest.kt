package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecipeUseCaseTest {
    private lateinit var subject: GetRecipeUseCase

    private val recipeRepository: RecipeRepository = mock()

    @BeforeEach
    fun setUp() {
        subject = GetRecipeUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        val recipe = TestResources.Recipes.cookinCanuck

        val recipeId = 123
        whenever(recipeRepository.findById(recipeId)).thenReturn(recipe)

        assertEquals(recipe, subject.execute(recipeId))
    }
}