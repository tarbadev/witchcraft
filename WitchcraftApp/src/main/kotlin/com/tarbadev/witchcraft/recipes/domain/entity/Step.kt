package com.tarbadev.witchcraft.recipes.domain.entity

data class Step(
    val id: Int = 0,
    val name: String = "",
    val note: StepNote = StepNote()
)
