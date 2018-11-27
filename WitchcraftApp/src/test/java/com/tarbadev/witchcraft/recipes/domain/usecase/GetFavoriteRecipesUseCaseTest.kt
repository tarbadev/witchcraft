package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class GetFavoriteRecipesUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()
    private lateinit var subject: GetFavoriteRecipesUseCase

    @BeforeEach
    fun setUp() {
        subject = GetFavoriteRecipesUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        val recipes = Arrays.asList(
            Recipe(),
            Recipe(),
            Recipe(),
            Recipe()
        )

        whenever(recipeRepository.findAllFavorite()).thenReturn(recipes)

        assertEquals(recipes, subject.execute())
    }
}