package com.tarbadev.witchcraft.weeks.domain.entity

data class Day(
    val id: Int = 0,
    val name: DayName,
    var meals: List<Meal> = emptyList()
)
