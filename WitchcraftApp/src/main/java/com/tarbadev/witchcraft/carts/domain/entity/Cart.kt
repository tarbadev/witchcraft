package com.tarbadev.witchcraft.carts.domain.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import java.time.LocalDateTime

data class Cart(
    val id: Int = 0,
    val recipes: List<Recipe> = emptyList(),
    val items: List<Item> = emptyList()
) {
    var createdAt: LocalDateTime = LocalDateTime.now()

    constructor(
        id: Int,
        recipes: List<Recipe>,
        items: List<Item>,
        createdAtDateTime: LocalDateTime
    ): this(id, recipes, items) {
        createdAt = createdAtDateTime
    }
}
