package com.tarbadev.witchcraft.carts.persistence.entity

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "carts")
data class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(cascade = [CascadeType.MERGE])
    val recipes: Set<RecipeEntity> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_id")
    @OrderBy("name ASC")
    val items: List<ItemEntity> = emptyList()
) {
    constructor(cart: Cart) : this(
        id = cart.id,
        createdAt = cart.createdAt,
        recipes = cart.recipes.map { RecipeEntity(it) }.toSet(),
        items = cart.items.map { ItemEntity(it) }
    )

    fun cart(): Cart {
        return Cart(
            id = id,
            createdAtDateTime = createdAt,
            recipes = recipes.map { it.recipe() },
            items = items.map { it.item() }
        )
    }
}
