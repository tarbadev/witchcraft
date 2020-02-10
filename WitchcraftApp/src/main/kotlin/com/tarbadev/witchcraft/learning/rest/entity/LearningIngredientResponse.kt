package com.tarbadev.witchcraft.learning.rest.entity

import com.tarbadev.witchcraft.converter.UnitHelper.getUnitShortName
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient

data class LearningIngredientResponse(
    val id: Int,
    val line: String,
    val name: String,
    val detail: String,
    val quantity: Double,
    val unit: String,
    val language: String,
    val valid: Boolean
) {
  companion object {
    fun fromLearningIngredient(learningIngredient: LearningIngredient) =
        LearningIngredientResponse(
            id = learningIngredient.id,
            name = learningIngredient.name,
            detail = learningIngredient.detail,
            quantity = "%.3f".format(learningIngredient.quantity.getValue().toDouble()).toDouble(),
            unit = getUnitShortName(learningIngredient.quantity),
            line = learningIngredient.line,
            valid = learningIngredient.valid,
            language = learningIngredient.language.toString()
        )
  }
}
