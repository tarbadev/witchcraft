package com.tarbadev.witchcraft.learning.domain.entity

import tech.units.indriya.ComparableQuantity

enum class Language { ENGLISH, FRENCH }

data class LearningIngredient(
    val id: Int,
    val line: String,
    val name: String,
    val detail: String,
    val quantity: ComparableQuantity<*>,
    val language: Language,
    val valid: Boolean
)
