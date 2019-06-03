package com.tarbadev.witchcraft.carts.persistence

import com.tarbadev.witchcraft.carts.domain.entity.Cart
import com.tarbadev.witchcraft.carts.domain.entity.Item
import com.tarbadev.witchcraft.carts.persistence.entity.CartEntity
import com.tarbadev.witchcraft.carts.persistence.entity.ItemEntity
import com.tarbadev.witchcraft.carts.persistence.repository.CartEntityRepository
import com.tarbadev.witchcraft.carts.persistence.repository.DatabaseCartRepository
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Arrays.asList

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseCartRepositoryTest(
    @Autowired private val entityManager: TestEntityManager,
    @Autowired private val cartEntityRepository: CartEntityRepository
) {
    private var databaseCartRepository: DatabaseCartRepository = DatabaseCartRepository(cartEntityRepository)

    @BeforeEach
    fun setup() {
        cartEntityRepository.deleteAll()
    }

    @Test
    fun findAll() {
        entityManager.persist(CartEntity())
        entityManager.persist(CartEntity())
        entityManager.persist(CartEntity())

        entityManager.flush()
        entityManager.clear()

        assertEquals(3, databaseCartRepository.findAll().size)
    }

    @Test
    fun save() {
        val recipes = listOf(entityManager.persistAndFlush(
            RecipeEntity(
                name = "Lasagna",
                ingredients = asList(
                    IngredientEntity(
                        name = "Ingredient 3",
                        unit = "cup",
                        quantity = 2.0
                    ),
                    IngredientEntity(
                        name = "Ingredient 1",
                        unit = "lb",
                        quantity = 2.0
                    ),
                    IngredientEntity(
                        name = "Ingredient 2",
                        unit = "oz",
                        quantity = 8.0
                    )
                ),
                steps = emptySet()
            )
        )
            .toRecipe())

        entityManager.clear()

        val items = asList(
            Item(
                name = "Ingredient 1",
                unit = "lb",
                quantity = 2.0,
                enabled = true
            ),
            Item(
                name = "Ingredient 2",
                unit = "oz",
                quantity = 8.0,
                enabled = true
            ),
            Item(
                name = "Ingredient 3",
                unit = "cup",
                quantity = 2.0,
                enabled = true
            )
        )
        val (id) = databaseCartRepository.save(Cart(
            recipes = recipes,
            items = items
        ))

        entityManager.clear()

        val savedCart = entityManager.find(CartEntity::class.java, id)

        assertEquals(3, savedCart.items.size)
        assertEquals(1, savedCart.recipes.size)
    }

    @Test
    fun findById() {
        val cart = entityManager.persistAndFlush(CartEntity()).toCart()

        entityManager.clear()

        assertEquals(cart, databaseCartRepository.findById(cart.id))
    }

    @Test
    fun findById_returnsItemsOrderedAlphabetically() {
        val cart = entityManager.persistAndFlush(
            CartEntity(
                items = listOf(
                    ItemEntity(name = "Parsley"),
                    ItemEntity(name = "Onions"),
                    ItemEntity(name = "Beef")
                )
            )
        )

        entityManager.clear()

        val items = cart.items
            .map { itemEntity -> itemEntity.toItem() }
            .sortedBy { it.name }

        assertIterableEquals(items, databaseCartRepository.findById(cart.id)!!.items)
    }

    @Test
    fun findById_returnsNullIfNotFound() {
        assertNull(databaseCartRepository.findById(1))
    }
}