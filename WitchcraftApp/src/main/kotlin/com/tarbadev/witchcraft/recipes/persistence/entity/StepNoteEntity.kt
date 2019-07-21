package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import javax.persistence.*

@Entity(name = "step_note")
data class StepNoteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column
    val comment: String = ""
) {
  fun toStepNote(): StepNote = StepNote(id, comment)

  constructor(stepNote: StepNote) : this(stepNote.id, stepNote.comment)
}
