package com.tarbadev.witchcraft.weeks.persistence.repository

import com.tarbadev.witchcraft.weeks.persistence.entity.WeekEntity
import org.springframework.data.jpa.repository.JpaRepository

import java.util.Optional

interface WeekEntityRepository : JpaRepository<WeekEntity, Int> {
    fun findByYearAndWeekNumber(year: Int?, weekNumber: Int?): Optional<WeekEntity>
}
