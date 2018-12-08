package com.tarbadev.witchcraft.weeks.persistence.repository

import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository
import com.tarbadev.witchcraft.weeks.persistence.entity.WeekEntity
import org.springframework.stereotype.Repository

@Repository
class DatabaseWeekRepository(private val weekEntityRepository: WeekEntityRepository) : WeekRepository {

    override fun findByYearAndWeekNumber(year: Int, weekNumber: Int): Week? {
        return weekEntityRepository.findByYearAndWeekNumber(year, weekNumber)
            .map { it.week() }.orElse(null)
    }

    override fun save(week: Week): Week {
        return weekEntityRepository.saveAndFlush(WeekEntity(week)).week()
    }
}
