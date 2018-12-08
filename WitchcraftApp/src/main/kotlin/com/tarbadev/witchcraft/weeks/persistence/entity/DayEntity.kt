package com.tarbadev.witchcraft.weeks.persistence.entity

import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.DayName
import javax.persistence.*

@Entity(name = "day")
data class DayEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0,
    private val name: String = "",
    @ManyToOne
    @JoinColumn(name = "lunch_recipe_id")
    private val lunch: RecipeEntity? = null,
    @ManyToOne
    @JoinColumn(name = "diner_recipe_id")
    private val diner: RecipeEntity? = null
) {
    constructor(day: Day) : this(
        id = day.id,
        name = day.name!!.name,
        lunch = if (day.lunch != null) RecipeEntity(day.lunch!!) else null,
        diner = if (day.diner != null) RecipeEntity(day.diner!!) else null
    )

    fun day(): Day {
        return Day(
            id = id,
            name = DayName.valueOf(name),
            lunch = lunch?.recipe(),
            diner = diner?.recipe()
        )
    }
}
