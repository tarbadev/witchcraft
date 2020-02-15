package com.tarbadev.witchcraft.recipes.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Notes
import com.tarbadev.witchcraft.recipes.domain.usecase.EditRecipeNotesUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.GetRecipeNotesUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.EditNotesRequest
import com.tarbadev.witchcraft.recipes.rest.entity.NotesResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(SpringExtension::class)
@WebMvcTest(RecipeNotesRestController::class)
class RecipeNotesRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
  @MockBean
  private lateinit var getRecipeNotesUseCase: GetRecipeNotesUseCase
  @MockBean
  private lateinit var editRecipeNotesUseCase: EditRecipeNotesUseCase

  @Test
  fun getNotes() {
    val notes = Notes(recipeId = 15, comment = "Some notes")
    val notesResponse = NotesResponse.fromNotes(notes)

    whenever(getRecipeNotesUseCase.execute(15)).thenReturn(notes)

    mockMvc.perform(get("/api/recipes/15/notes"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(notesResponse)))
  }

  @Test
  fun `getNotes returns 404 when notes are not found`() {
    whenever(getRecipeNotesUseCase.execute(15)).thenReturn(null)

    mockMvc.perform(get("/api/recipes/15/notes"))
        .andExpect(status().isNotFound)
  }

  @Test
  fun editNotes() {
    val notesRequest = EditNotesRequest(recipeId = 15, notes = "Some notes")
    val notes = notesRequest.toNotes()
    val notesResponse = NotesResponse.fromNotes(notes)

    whenever(editRecipeNotesUseCase.execute(notes)).thenReturn(notes)

    mockMvc.perform(post("/api/recipes/15/notes")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(jacksonObjectMapper().writeValueAsString(notesRequest))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(jacksonObjectMapper().writeValueAsString(notesResponse)))
  }
}