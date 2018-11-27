package com.tarbadev.witchcraft.weeks.domain.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

data class Day(
    var id: Int = 0,
    var name: DayName? = null,
    var lunch: Recipe? = null,
    var diner: Recipe? = null
)
