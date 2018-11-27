package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Item
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "items")
data class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = ""
) {
    constructor(item: Item) : this(
        id = item.id,
        name = item.name,
        quantity = item.quantity,
        unit = item.unit
    )

    fun item(): Item {
        return Item(
            id = id,
            name = name,
            quantity = quantity,
            unit = unit
        )
    }
}
