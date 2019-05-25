package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.rest.entity.RecipeResponse
import com.tarbadev.witchcraft.weeks.domain.entity.Day

data class DayResponse(
    val id: Int,
    val name: String,
    val lunch: RecipeResponse?,
    val diner: RecipeResponse?
) {
  companion object {
    fun fromDay(day: Day): DayResponse {
      val dayLunch = day.lunch
      var lunch: RecipeResponse? = null
      if (dayLunch != null) {
        lunch = RecipeResponse.fromRecipe(dayLunch)
      }
      val dayDiner = day.diner
      var diner: RecipeResponse? = null
      if (dayDiner != null) {
        diner = RecipeResponse.fromRecipe(dayDiner)
      }

      return DayResponse(
          day.id,
          day.name.name,
          lunch,
          diner
      )
    }
  }
}
