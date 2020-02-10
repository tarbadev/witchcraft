package com.tarbadev.witchcraft.learning.rest.entity

data class LearningIngredientRequest(
    val name: String,
    val detail: String,
    val quantity: Double,
    val unit: String,
    val language: String
)
