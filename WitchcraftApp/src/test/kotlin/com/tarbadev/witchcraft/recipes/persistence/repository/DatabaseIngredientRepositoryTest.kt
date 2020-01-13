package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.converter.ounce
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseIngredientRepositoryTest(
    @Autowired private val ingredientEntityRepository: IngredientEntityRepository,
    @Autowired private val entityManager: TestEntityManager
) {
  private var databaseIngredientRepository: DatabaseIngredientRepository = DatabaseIngredientRepository(ingredientEntityRepository)

  @Test
  fun save() {
    val ingredient = databaseIngredientRepository.save(Ingredient(name = "Some Ingredient", quantity = 2.ounce))
    val expectedIngredient = Ingredient(
        id = ingredient.id,
        name = "Some Ingredient",
        quantity = 2.ounce
    )

    assertThat(ingredient).isEqualTo(expectedIngredient)
  }

  @Test
  fun delete() {
    val ingredientEntity = entityManager.persistAndFlush(IngredientEntity(name = "Some Ingredient", quantity = 2.0, unit = "oz"))

    entityManager.clear()

    Assertions.assertNotNull(entityManager.find(IngredientEntity::class.java, ingredientEntity.id))

    databaseIngredientRepository.delete(ingredientEntity.id)

    entityManager.clear()

    Assertions.assertNull(entityManager.find(IngredientEntity::class.java, ingredientEntity.id))
  }
}