package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import com.tarbadev.witchcraft.weeks.domain.entity.MealType

data class DayRequest(
    val id: Int,
    val name: String,
    val lunch: List<MealRequest> = emptyList(),
    val diner: List<MealRequest> = emptyList()
) {
  fun toDay() = Day(
      id,
      DayName.valueOf(name),
      lunch.map { it.toMeal(MealType.LUNCH) }
          .plus(diner.map { it.toMeal(MealType.DINER) })
  )
}
