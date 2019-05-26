package com.tarbadev.witchcraft.carts.domain.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import java.time.Instant

data class Cart(
    val id: Int = 0,
    val recipes: List<Recipe> = emptyList(),
    val items: List<Item> = emptyList()
) {
    var createdAt: Instant = Instant.now()

    constructor(
        id: Int,
        recipes: List<Recipe>,
        items: List<Item>,
        createdAtDateTime: Instant
    ): this(id, recipes, items) {
        createdAt = createdAtDateTime
    }
}
