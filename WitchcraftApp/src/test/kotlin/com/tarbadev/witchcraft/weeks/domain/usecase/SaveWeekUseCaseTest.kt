package com.tarbadev.witchcraft.weeks.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import java.util.Arrays.asList

@ActiveProfiles("test")
class SaveWeekUseCaseTest {
    private val weekRepository: WeekRepository = mock()
    private val recipeRepository: RecipeRepository = mock()

    private lateinit var subject: SaveWeekUseCase

    @BeforeEach
    fun setUp() {
        subject = SaveWeekUseCase(weekRepository, recipeRepository)
    }

    @Test
    fun execute() {
        val lasagna = Recipe(id = 213, name = "lasagna")
        val tartiflette = Recipe(id = 897, name = "tartiflette")

        val week = Week(
            id = 345,
            year = 2018,
            weekNumber = 12,
            days = asList(
                Day(
                    id = 234,
                    name = DayName.MONDAY,
                    lunch = Recipe(id = 213)
                ),
                Day(
                    id = 789,
                    name = DayName.THURSDAY,
                    diner = Recipe(id = 897)
                )
            ))

        val weekWithRecipes = Week(
            id = 345,
            year = 2018,
            weekNumber = 12,
            days = asList(
                Day(
                    id = 234,
                    name = DayName.MONDAY,
                    lunch = lasagna
                ),
                Day(
                    id = 789,
                    name = DayName.THURSDAY,
                    diner = tartiflette
                )
            ))

        whenever(recipeRepository.findById(213)).thenReturn(lasagna)
        whenever(recipeRepository.findById(897)).thenReturn(tartiflette)

        subject.execute(week)

        verify(weekRepository).save(weekWithRecipes)
    }
}