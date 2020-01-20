package com.tarbadev.witchcraft.weeks.persistence.entity

import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import com.tarbadev.witchcraft.weeks.domain.entity.Meal
import com.tarbadev.witchcraft.weeks.domain.entity.MealType
import javax.persistence.*

@Entity(name = "meal")
data class MealEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0,
    val mealType: String = "",
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    val recipe: RecipeEntity? = null
) {
    constructor(meal: Meal) : this(
        id = meal.id,
        mealType = meal.mealType.toString(),
        recipe = RecipeEntity.fromRecipe(meal.recipe)
    )

    fun meal(): Meal {
        return Meal(
            id = id,
            mealType = MealType.valueOf(mealType),
            recipe = recipe!!.toRecipe()
        )
    }
}
