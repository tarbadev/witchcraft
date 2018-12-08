package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

class SetFavoriteRecipeUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()
    private lateinit var subject: SetFavoriteRecipeUseCase

    @BeforeEach
    fun setUp() {
        subject = SetFavoriteRecipeUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        subject.execute(123, true)

        verify<RecipeRepository>(recipeRepository).setFavorite(123, true)
    }
}