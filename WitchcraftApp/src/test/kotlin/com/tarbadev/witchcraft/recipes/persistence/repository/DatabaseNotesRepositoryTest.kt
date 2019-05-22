package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.persistence.entity.NotesEntity
import org.junit.jupiter.api.Assertions
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
class DatabaseNotesRepositoryTest(
    @Autowired private val notesEntityRepository: NotesEntityRepository,
    @Autowired private val entityManager: TestEntityManager
) {
  private lateinit var databaseNotesRepository: DatabaseNotesRepository

  @BeforeEach
  fun setUp() {
    notesEntityRepository.deleteAll()

    databaseNotesRepository = DatabaseNotesRepository(notesEntityRepository)
  }

  @Test
  fun findById() {
    val notes = toDomain(
        entityManager.persistAndFlush(NotesEntity(
            recipeId = 1,
            comment = "Some comment"
        ))
    )

    entityManager.clear()

    Assertions.assertEquals(notes, databaseNotesRepository.findByRecipeId(notes.recipeId))
  }

  private fun toDomain(notesEntity: NotesEntity): Notes {
    return Notes(
        recipeId = notesEntity.recipeId,
        comment = notesEntity.comment
    )
  }
}