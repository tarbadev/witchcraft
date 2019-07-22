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
    @OrderBy("id ASC")
    var steps: Set<StepEntity> = emptySet(),
    @Column(name = "archived", nullable = false)
    var isArchived: Boolean = false,
    var portions: Int? = null
) {
  companion object {
    fun fromRecipe(recipe: Recipe): RecipeEntity {
      return RecipeEntity(
          recipe.id,
          recipe.originUrl,
          recipe.name,
          recipe.imgUrl,
          recipe.favorite,
          recipe.ingredients.map { IngredientEntity(it) },
          recipe.steps.map { StepEntity(it) }.toSet(),
          recipe.isArchived,
          recipe.portions
      )
    }
  }

  fun toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name.toLowerCase(),
        originUrl = originUrl,
        imgUrl = imgUrl,
        favorite = favorite,
        ingredients = ingredients.map { it.toIngredient() },
        steps = steps.map { it.toStep() },
        isArchived = isArchived,
        portions = portions
    )
  }
}
