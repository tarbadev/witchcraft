package com.tarbadev.witchcraft.weeks.persistence.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Week
import javax.persistence.*

@Entity(name = "week")
data class WeekEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0,
    private val year: Int = 0,
    private val weekNumber: Int = 0,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "week_id")
    private val days: List<DayEntity> = emptyList()
) {
    constructor(week: Week) : this(
        id = week.id,
        year = week.year,
        weekNumber = week.weekNumber,
        days = week.days.map { DayEntity(it) }
    )

    fun week(): Week {
        return Week(
            id = id,
            weekNumber = weekNumber,
            year = year,
            days = days.map { it.day() }.sortedBy { it.name }
        )
    }
}
