package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class DeleteRecipeUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()

    private lateinit var deleteRecipeUseCase: DeleteRecipeUseCase

    @BeforeEach
    fun setUp() {
        deleteRecipeUseCase = DeleteRecipeUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        deleteRecipeUseCase.execute(123)

        verify<RecipeRepository>(recipeRepository).delete(123)
    }
}