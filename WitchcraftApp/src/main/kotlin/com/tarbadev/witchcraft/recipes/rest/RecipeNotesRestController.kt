package com.tarbadev.witchcraft.recipes.rest

import com.tarbadev.witchcraft.recipes.domain.usecase.EditRecipeNotesUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.GetRecipeNotesUseCase
import com.tarbadev.witchcraft.recipes.rest.entity.EditNotesRequest
import com.tarbadev.witchcraft.recipes.rest.entity.NotesResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes/notes")
class RecipeNotesRestController(
    private val getRecipeNotesUseCase: GetRecipeNotesUseCase,
    private val editRecipeNotesUseCase: EditRecipeNotesUseCase
) {
    @GetMapping
    fun getNotes(@PathVariable recipeId: Int): NotesResponse? {
        val notes = getRecipeNotesUseCase.execute(recipeId) ?: throw NotesNotFoundException()

        return NotesResponse.fromNotes(notes)
    }

    @PostMapping
    fun editNotes(@RequestBody editNotesRequest: EditNotesRequest): NotesResponse? {
        return NotesResponse.fromNotes(editRecipeNotesUseCase.execute(editNotesRequest.toNotes()))
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Notes not found")
    inner class NotesNotFoundException : RuntimeException()
}
