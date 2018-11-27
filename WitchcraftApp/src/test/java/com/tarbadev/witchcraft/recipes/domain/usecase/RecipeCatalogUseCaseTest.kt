package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class RecipeCatalogUseCaseTest {
    private val recipeRepository: RecipeRepository = mock()
    private lateinit var recipeCatalogUseCase: RecipeCatalogUseCase

    @BeforeEach
    fun setUp() {
        recipeCatalogUseCase = RecipeCatalogUseCase(recipeRepository)
    }

    @Test
    fun execute() {
        val recipes = Arrays.asList(
            Recipe(),
            Recipe(),
            Recipe()
        )

        whenever(recipeRepository.findAll()).thenReturn(recipes)

        assertEquals(recipes, recipeCatalogUseCase.execute())
    }
}