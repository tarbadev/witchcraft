package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import javax.persistence.*

@Entity(name = "step")
data class StepEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(length = 1000)
    val name: String = "",

    @OneToOne
    @JoinColumn(name = "note_id")
    val note: StepNoteEntity? = null
) {

    constructor(step: Step) : this(
        id = step.id,
        name = step.name,
        note = StepNoteEntity(step.note)
    )

    fun toStep(): Step {
        return Step(
            id = id,
            name = name,
            note = note?.toStepNote() ?: StepNote()
        )
    }
}
