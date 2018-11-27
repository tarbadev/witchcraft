package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import javax.persistence.*

@Entity
data class StepEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(length = 1000)
    val name: String = ""
) {

    constructor(step: Step) : this(
        id = step.id,
        name = step.name
    )

    fun step(): Step {
        return Step(
            id = id,
            name = name
        )
    }
}
