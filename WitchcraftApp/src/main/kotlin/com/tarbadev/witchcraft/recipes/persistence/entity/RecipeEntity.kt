package com.tarbadev.witchcraft.recipes.persistence.entity

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import javax.persistence.*

@Entity(name = "recipe")
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var originUrl: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var favorite: Boolean = false,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "recipe_id")
    @OrderBy("name ASC")
    var ingredients: List<IngredientEntity> = emptyList(),
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "recipe_id")
    var steps: Set<StepEntity> = emptySet(),
    @Column(name = "archived", nullable = false)
    var isArchived: Boolean = false
) {
    constructor(recipe: Recipe) : this(
        id = recipe.id,
        originUrl = recipe.originUrl,
        name = recipe.name,
        imgUrl = recipe.imgUrl,
        favorite = recipe.favorite,
        ingredients = recipe.ingredients.map { IngredientEntity(it) },
        steps = recipe.steps.map { StepEntity(it) }.toSet(),
        isArchived = recipe.isArchived
    )

    fun recipe(): Recipe {
        return Recipe(
            id = id,
            name = name.toLowerCase(),
            originUrl = originUrl,
            imgUrl = imgUrl,
            favorite = favorite,
            ingredients = ingredients.map { it.ingredient() },
            steps = steps.map { it.step() },
            isArchived = isArchived
        )
    }
}
