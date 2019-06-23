package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.converter.UnitHelper.getUnitShortName
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "ingredient")
data class IngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = ""
) {
    constructor(ingredient: Ingredient) : this(
        id = ingredient.id,
        name = ingredient.name,
        quantity = ingredient.quantity.getValue().toDouble(),
        unit = getUnitShortName(ingredient.quantity)
    )

    fun toIngredient(): Ingredient {
        return Ingredient(
            id = id,
            name = name,
            quantity = getQuantity(quantity, unit)
        )
    }
}
