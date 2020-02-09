package com.tarbadev.witchcraft.learning.persistence.entity

import com.tarbadev.witchcraft.converter.UnitHelper
import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.rest.entity.LearningIngredientResponse
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "ml_ingredient")
data class LearningIngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = "",
    val line: String = "",
    val language: String = "",
    val valid: Boolean = false
) {
    fun toLearningIngredient(): LearningIngredient {
        return LearningIngredient(
            id = id,
                line = line,
                name = name,
                quantity = getQuantity(quantity, unit),
                language = Language.valueOf(language),
                valid = valid
        )
    }

    companion object {
        fun fromLearningIngredient(learningIngredient: LearningIngredient) =
            LearningIngredientEntity(
                id = learningIngredient.id,
                name = learningIngredient.name,
                quantity = "%.3f".format(learningIngredient.quantity.getValue().toDouble()).toDouble(),
                unit = UnitHelper.getUnitShortName(learningIngredient.quantity),
                line = learningIngredient.line,
                valid = learningIngredient.valid,
                language = learningIngredient.language.toString()
            )
    }
}
