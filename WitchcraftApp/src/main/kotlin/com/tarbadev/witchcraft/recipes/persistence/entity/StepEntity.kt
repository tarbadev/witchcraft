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

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "step_id")
    val notes: List<StepNoteEntity> = emptyList()
) {

  constructor(step: Step) : this(
      id = step.id,
      name = step.name,
      notes = if (step.note.comment.isNotEmpty())
        listOf(StepNoteEntity(step.note))
      else
        emptyList()
  )

  fun toStep(): Step {
    return Step(
        id = id,
        name = name,
        note = notes.map { it.toStepNote() }.firstOrNull() ?: StepNote()
    )
  }
}
