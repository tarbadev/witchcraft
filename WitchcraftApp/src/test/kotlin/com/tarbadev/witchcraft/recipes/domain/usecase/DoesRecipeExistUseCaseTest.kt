package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DoesRecipeExistUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()
    private lateinit var subject: DoesRecipeExistUseCase

    @BeforeEach
    fun setUp() {
        subject = DoesRecipeExistUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        val id = 32

        whenever(recipeRepository.existsById(id)).thenReturn(true)

        assertTrue(subject.execute(id))
    }
}