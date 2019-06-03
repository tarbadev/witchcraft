package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import javax.persistence.*

@Entity(name = "cart")
data class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @CreationTimestamp
    var createdAt: Instant = Instant.now(),

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "cart_recipe",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_id")]
    )
    val recipes: Set<RecipeEntity> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_id")
    @OrderBy("name ASC")
    val items: List<ItemEntity> = emptyList()
) {
    companion object {
        fun fromCart(cart: Cart): CartEntity {
            return CartEntity(
                cart.id,
                cart.createdAt,
                cart.recipes.map { RecipeEntity.fromRecipe(it) }.toSet(),
                cart.items.map { ItemEntity.fromItem(it) }
            )
        }
    }

    fun toCart(): Cart {
        return Cart(
            id = id,
            createdAtDateTime = createdAt,
            recipes = recipes.map { it.toRecipe() },
            items = items.map { it.toItem() }
        )
    }
}
