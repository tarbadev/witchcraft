package com.tarbadev.witchcraft.learning.persistence.repository

import com.tarbadev.witchcraft.converter.teaspoon
import com.tarbadev.witchcraft.learning.domain.entity.Language
import com.tarbadev.witchcraft.learning.domain.entity.LearningIngredient
import com.tarbadev.witchcraft.learning.persistence.entity.LearningIngredientEntity
import org.assertj.core.api.Assertions.assertThat
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

    assertThat(databaseIngredientToValidateRepository.findAll()).hasSize(expectedRecipes.size)
  }

  @Test
  fun findById() {
    val learningIngredientEntity = entityManager.persist(LearningIngredientEntity(
        0,
        "some ingredient",
        2.0,
        "tsp",
        "Some ingredient line",
        Language.ENGLISH.toString(),
        true
    ))
    val learningIngredient = learningIngredientEntity.toLearningIngredient()

    entityManager.flush()
    entityManager.clear()

    assertThat(databaseIngredientToValidateRepository.findById(learningIngredientEntity.id)).isEqualTo(learningIngredient)
  }

  @Test
  fun save() {
    val learningIngredient = LearningIngredient(
        0,
        "Some ingredient line",
        "some ingredient",
        2.teaspoon,
        Language.ENGLISH,
        true
    )

    val actualLearningIngredient = databaseIngredientToValidateRepository.save(learningIngredient)

    entityManager.clear()

    val learningIngredientEntity = entityManager.find(LearningIngredientEntity::class.java, actualLearningIngredient.id)

    assertThat(actualLearningIngredient).isEqualTo(learningIngredientEntity.toLearningIngredient())
  }
}