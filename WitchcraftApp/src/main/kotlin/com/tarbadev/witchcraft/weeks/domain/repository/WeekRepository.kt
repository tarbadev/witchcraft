package com.tarbadev.witchcraft.weeks.domain.repository

import com.tarbadev.witchcraft.weeks.domain.entity.Week

interface WeekRepository {
    fun findByYearAndWeekNumber(year: Int, weekNumber: Int): Week?
    fun save(week: Week): Week
}
