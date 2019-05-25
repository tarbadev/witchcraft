package com.tarbadev.witchcraft.weeks.domain.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class Day(
    val id: Int = 0,
    val name: DayName,
    var lunch: Recipe? = null,
    var diner: Recipe? = null
)
