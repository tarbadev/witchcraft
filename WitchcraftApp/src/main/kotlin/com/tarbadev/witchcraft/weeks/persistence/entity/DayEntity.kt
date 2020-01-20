package com.tarbadev.witchcraft.weeks.persistence.entity

import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import javax.persistence.*

@Entity(name = "day")
data class DayEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0,
    private val name: String = "",
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "day_id")
    private val meals: List<MealEntity> = ArrayList()
) {
  constructor(day: Day) : this(
      id = day.id,
      name = day.name.name,
      meals = day.meals.map { MealEntity(it) }
  )

  fun day(): Day {
    return Day(
        id = id,
        name = DayName.valueOf(name),
        meals = meals.map { it.meal() }
    )
  }
}
