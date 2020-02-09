package com.tarbadev.witchcraft.learning.persistence.repository

import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.persistence.entity.LearningIngredientEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
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
class DatabaseLearningIngredientRepositoryTest(
    @Autowired private val learningIngredientEntityRepository: LearningIngredientEntityRepository,
    @Autowired private val entityManager: TestEntityManager
) {
  private val databaseIngredientToValidateRepository = DatabaseLearningIngredientRepository(learningIngredientEntityRepository)

  @BeforeEach
  fun setUp() {
    learningIngredientEntityRepository.deleteAll()
  }

  @Test
  fun findAll() {
    val expectedRecipes = listOf(
        entityManager.persist(LearningIngredientEntity(
            0,
            "some ingredient",
            2.0,
            "tsp",
            "Some ingredient line",
            Language.ENGLISH.toString(),
            true
        )),
        entityManager.persist(LearningIngredientEntity(
            0,
            "another ingredient",
            350.0,
            "cl",
            "Another ingredient line",
            Language.FRENCH.toString(),
            false
        ))
    )

    entityManager.flush()
    entityManager.clear()

    assertEquals(expectedRecipes.size, databaseIngredientToValidateRepository.findAll().size)
  }
}