package com.tarbadev.witchcraft.carts.domain.entity

data class Item(
    val id: Int = 0,
    val name: String,
    val quantity: Double,
    val unit: String
)
