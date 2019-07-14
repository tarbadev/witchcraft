import { push } from 'connected-react-router'
import {
  deleteRecipe,
  deleteRecipeCallback,
  getRecipe,
  getRecipeError,
  getRecipeNotes,
  getRecipesNotesError,
  getRecipesNotesSuccess,
  getRecipeSuccess,
  hideEditableNotes,
  setFavorite,
  setFavoriteSuccess,
  showEditableNotes,
  updateNotes,
  updateNotesSuccess,
  updateRecipe,
  updateRecipeSuccess,
} from './RecipeActions'

import promisedRecipeList from 'test-resources/recipeList.json'
import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

describe('RecipeActions', () => {
  describe('getRecipe', () => {
    it('calls the witchcraft API and sets the state with the recipe', () => {
      const dispatchSpy = jest.fn()

      getRecipe(1)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/recipes/1',
        method: 'GET',
        onSuccess: getRecipeSuccess,
        onError: getRecipeError,
      }))
    })
  })

  describe('getRecipeSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      getRecipeSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(setState('recipe', promisedRecipeList.recipes[0]))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('editRecipe.form', promisedRecipeList.recipes[0]))
    })
  })

  describe('getRecipeError', () => {
    it('error callback redirects to recipes', () => {
      const dispatchSpy = jest.fn()
      getRecipeError()(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(push('/recipes'))
    })
  })

  describe('setFavorite', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      setFavorite(2, true)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/recipes/2',
        method: 'PATCH',
        body: { favorite: true },
        onSuccess: setFavoriteSuccess,
      }))
    })
  })

  describe('setFavoriteSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      setFavoriteSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(setState('recipe', promisedRecipeList.recipes[0]))
    })
  })

  describe('updateRecipe', () => {
    it('success callback updates the recipe on the backend', () => {
      const dispatchSpy = jest.fn()
      const form = {
        id: 12,
        name: 'Mini Goat Cheese Stuffed Potato Appetizers',
        url: 'http://example.com/recipe/32434',
        imgUrl: 'http://example.com/recipe/32434.png',
        ingredients: [{
          name: 'Ingredient 1',
          unit: 'tbsp',
          quantity: 12,
        }, {
          name: 'Ingredient 2',
          unit: 'cup',
          quantity: 4,
        }],
        steps: [{
          name: 'Add sugar',
        }, {
          name: 'Mix with flour',
        }],
      }

      updateRecipe(form)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: `/api/recipes/${form.id}/update`,
        method: 'PUT',
        body: form,
        onSuccess: updateRecipeSuccess,
      }))
    })
  })

  describe('updateRecipeSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      updateRecipeSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(push(`/recipes/${promisedRecipeList.recipes[0].id}`))
    })
  })

  describe('deleteRecipe', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      deleteRecipe(2)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/recipes/2',
        method: 'DELETE',
        onSuccess: deleteRecipeCallback,
        onError: deleteRecipeCallback,
      }))
    })
  })

  describe('deleteRecipeCallback', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      deleteRecipeCallback()(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.isDeleting', false))
      expect(dispatchSpy).toHaveBeenCalledWith(push('/recipes'))
    })
  })

  describe('updateNotes', () => {
    it('success callback updates the recipe on the backend', () => {
      const dispatchSpy = jest.fn()
      const notes = 'Keep it going'
      const id = 1

      updateNotes(id, notes)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: `/api/recipes/${id}/notes`,
        method: 'POST',
        body: { recipeId: id, notes },
        onSuccess: updateNotesSuccess,
      }))
    })
  })

  describe('updateNotesSuccess', () => {
    it('sets the page in non editable mode', () => {
      const dispatchSpy = jest.fn()

      updateNotesSuccess({ recipeId: 14, notes: 'New Notes' })(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.notes', 'New Notes'))
    })
  })

  describe('getRecipeNotes', () => {
    it('gets the notes for a given recipe id', () => {
      const dispatchSpy = jest.fn()
      const id = 1

      getRecipeNotes(id)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: `/api/recipes/${id}/notes`,
        method: 'GET',
        onSuccess: getRecipesNotesSuccess,
        onError: getRecipesNotesError,
      }))
    })
  })

  describe('getRecipesNotesSuccess', () => {
    it('sets the notes in the state', () => {
      const dispatchSpy = jest.fn()
      const newNotes = 'New Notes'

      getRecipesNotesSuccess({ notes: newNotes })(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.notes', newNotes))
    })
  })

  describe('getRecipesNotesError', () => {
    it('sets the empty notes in the state', () => {
      const dispatchSpy = jest.fn()

      getRecipesNotesError({ message: 'Notes not found' })(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.notes', ''))
    })
  })

  describe('showEditableNotes', () => {
    it('saves the current value of notes into editableNotes and display form', () => {
      const dispatchSpy = jest.fn()
      const getState = jest.fn(() => ({ app: { pages: { recipePage: { notes: 'Some notes' } } } }))

      showEditableNotes()(dispatchSpy, getState)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.notesInput', 'Some notes'))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.editableNotes', true))
    })
  })

  describe('hideEditableNotes', () => {
    it('clears the current value of notes into editableNotes and hide form', () => {
      const dispatchSpy = jest.fn()

      hideEditableNotes()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.notesInput', ''))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.recipePage.editableNotes', false))
    })
  })
})
