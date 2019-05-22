package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.persistence.entity.NotesEntity
import org.assertj.core.api.Assertions.assertThat
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

    assertThat(databaseNotesRepository.findByRecipeId(notes.recipeId)).isEqualTo(notes)
  }

  @Test
  fun save() {
    val notes = Notes(
        recipeId = 1,
        comment = "Some comment"
    )

    val returnedNotes = databaseNotesRepository.save(notes)
    entityManager.clear()

    assertThat(toDomain(entityManager.find(NotesEntity::class.java, returnedNotes.id))).isEqualTo(returnedNotes)
  }

  @Test
  fun `save erases existing notes if exists`() {
    val notes = Notes(
        recipeId = 1,
        comment = "Some comment"
    )

    val originalNotes = entityManager.persistAndFlush(NotesEntity(recipeId = 1, comment = "An old comment"))
    entityManager.clear()

    val returnedNotes = databaseNotesRepository.save(notes)
    entityManager.clear()

    assertThat(toDomain(entityManager.find(NotesEntity::class.java, originalNotes.id))).isEqualTo(returnedNotes)
  }

  private fun toDomain(notesEntity: NotesEntity): Notes {
    return Notes(
        id = notesEntity.id,
        recipeId = notesEntity.recipeId,
        comment = notesEntity.comment
    )
  }
}