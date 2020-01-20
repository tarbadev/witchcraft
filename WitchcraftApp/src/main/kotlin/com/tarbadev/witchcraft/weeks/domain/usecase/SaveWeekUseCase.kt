package com.tarbadev.witchcraft.weeks.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import org.springframework.stereotype.Component

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
              meals = day.meals.map { it.copy(recipe = recipeRepository.findById(it.recipe.id)!!) }
          )
        }
    )
    return weekRepository.save(weekWithRecipes)
  }
}
