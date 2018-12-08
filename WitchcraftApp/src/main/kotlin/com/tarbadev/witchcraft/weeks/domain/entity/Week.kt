package com.tarbadev.witchcraft.weeks.domain.entity

data class Week(
    var id: Int = 0,
    var year: Int = 0,
    var weekNumber: Int = 0,
    var days: List<Day> = emptyList()
)
