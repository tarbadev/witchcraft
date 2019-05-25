package com.tarbadev.witchcraft.weeks.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName

data class DayRequest(
    val id: Int,
    val name: String,
    val lunch: Int? = null,
    val diner: Int? = null
) {
  fun toDay(): Day {
    val recipeLunch = if (lunch == null) {
      null
    } else {
      Recipe(id = lunch)
    }

    val recipeDiner = if (diner == null) {
      null
    } else {
      Recipe(id = diner)
    }

    return Day(
        id,
        DayName.valueOf(name),
        recipeLunch,
        recipeDiner
    )
  }
}
