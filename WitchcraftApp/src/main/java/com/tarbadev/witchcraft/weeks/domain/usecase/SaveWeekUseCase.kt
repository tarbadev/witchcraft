package com.tarbadev.witchcraft.weeks.domain.usecase

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.springframework.stereotype.Component

import java.util.stream.Collectors

@Component
class SaveWeekUseCase(private val weekRepository: WeekRepository, private val recipeRepository: RecipeRepository) {

    fun execute(week: Week): Week {
        val weekWithRecipes = Week(
            id = week.id,
            year = week.year,
            weekNumber = week.weekNumber,
            days = week.days.map { day ->
                Day(
                    id = day.id,
                    name = day.name,
                    lunch = if (day.lunch != null) recipeRepository.findById(day.lunch!!.id) else null,
                    diner = if (day.diner != null) recipeRepository.findById(day.diner!!.id) else null
                )
            }
        )
        return weekRepository.save(weekWithRecipes)
    }
}
