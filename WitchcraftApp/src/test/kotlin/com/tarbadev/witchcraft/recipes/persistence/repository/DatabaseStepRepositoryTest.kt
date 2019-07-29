package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity
import org.assertj.core.api.Assertions.assertThat
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
class DatabaseStepRepositoryTest(
    @Autowired private val entityManager: TestEntityManager,
    @Autowired private val stepEntityRepository: StepEntityRepository
) {
  private var databaseStepRepository: DatabaseStepRepository = DatabaseStepRepository(stepEntityRepository)

  @Test
  fun findById() {
    val step = entityManager.persistAndFlush(StepEntity()).toStep()

    entityManager.clear()

    assertThat(databaseStepRepository.findById(step.id)).isEqualTo(step)
  }

  @Test
  fun save() {
    val step = databaseStepRepository.save(Step(name = "Step 1", note = StepNote(comment = "Comment 1")))
    val expectedStep = Step(
        id = step.id,
        name = "Step 1",
        note = StepNote(id = step.note.id, comment = "Comment 1")
    )

    assertThat(step).isEqualTo(expectedStep)
  }
}