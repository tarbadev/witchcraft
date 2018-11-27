package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import javax.persistence.*

@Entity(name = "recipes")
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,
    val originUrl: String = "",
    val name: String = "",
    val imgUrl: String = "",
    var favorite: Boolean = false,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "recipe_id")
    @OrderBy("name ASC")
    val ingredients: List<IngredientEntity> = emptyList(),
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "recipe_id")
    val steps: Set<StepEntity> = emptySet()
) {
    constructor(recipe: Recipe) : this(
        id = recipe.id,
        originUrl = recipe.originUrl,
        name = recipe.name,
        imgUrl = recipe.imgUrl,
        favorite = recipe.favorite,
        ingredients = recipe.ingredients.map { IngredientEntity(it) },
        steps = recipe.steps.map { StepEntity(it) }.toSet()
    )

    fun recipe(): Recipe {
        return Recipe(
            id = id,
            name = name.toLowerCase(),
            url = "/recipes/$id",
            originUrl = originUrl,
            imgUrl = imgUrl,
            favorite = favorite,
            ingredients = ingredients.map { it.ingredient() },
            steps = steps.map { it.step() }
        )
    }
}
