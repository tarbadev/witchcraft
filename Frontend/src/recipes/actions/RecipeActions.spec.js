import {
  getRecipe,
  getRecipeSuccess,
  getRecipeError,
  setFavorite,
  setFavoriteSuccess,
  updateRecipe,
  updateRecipeSuccess,
  deleteRecipe,
  deleteRecipeCallback,
} from 'src/recipes/actions/RecipeActions'

import promisedRecipeList from 'test-resources/recipeList.json'
import { getRecipeNotes, getRecipesNotesSuccess, updateNotes, updateNotesSuccess } from './RecipeActions'
import { setState } from '../../RootReducer'
import { fetchAction } from '../../WitchcraftMiddleware'

describe('RecipeActions', () => {
  describe('getRecipe', () => {
    it('calls the witchcraft API and sets the state with the recipe', () => {
      const dispatchSpy = jest.fn()

      getRecipe(1)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: '/api/recipes/1',
        method: 'GET',
        body: undefined,
        onSuccess: getRecipeSuccess,
        onError: getRecipeError,
      })
    })
  })

  describe('getRecipeSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      getRecipeSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'recipe',
        payload: promisedRecipeList.recipes[0],
      })

      const form = {
        id: promisedRecipeList.recipes[0].id,
        name: promisedRecipeList.recipes[0].name,
        url: promisedRecipeList.recipes[0].originUrl,
        imgUrl: promisedRecipeList.recipes[0].imgUrl,
        ingredients: promisedRecipeList.recipes[0].ingredients,
        steps: promisedRecipeList.recipes[0].steps,
      }
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'editRecipe.form',
        payload: form,
      })
    })
  })

  describe('getRecipeError', () => {
    it('error callback redirects to recipes', () => {
      const dispatchSpy = jest.fn()
      getRecipeError()(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: '@@router/CALL_HISTORY_METHOD',
        payload: { method: 'push', args: ['/recipes'] },
      })
    })
  })

  describe('setFavorite', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      setFavorite(2, true)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: '/api/recipes/2',
        method: 'PATCH',
        body: { favorite: true },
        onSuccess: setFavoriteSuccess,
        onError: undefined,
      })
    })
  })

  describe('setFavoriteSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      setFavoriteSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'recipe',
        payload: promisedRecipeList.recipes[0],
      })
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
          unit: 'Add sugar',
        }, {
          name: 'Mix with flour',
        }],
      }

      updateRecipe(form)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: `/api/recipes/${form.id}/update`,
        method: 'PUT',
        body: form,
        onSuccess: updateRecipeSuccess,
        onError: undefined,
      })
    })
  })

  describe('updateRecipeSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      updateRecipeSuccess(promisedRecipeList.recipes[0])(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: '@@router/CALL_HISTORY_METHOD',
        payload: { method: 'push', args: [`/recipes/${promisedRecipeList.recipes[0].id}`] },
      })
    })
  })

  describe('deleteRecipe', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      deleteRecipe(2)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: '/api/recipes/2',
        method: 'DELETE',
        body: undefined,
        onSuccess: deleteRecipeCallback,
        onError: deleteRecipeCallback,
      })
    })
  })

  describe('deleteRecipeCallback', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      deleteRecipeCallback()(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'recipePage.isDeleting',
        payload: false,
      })
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: '@@router/CALL_HISTORY_METHOD',
        payload: { method: 'push', args: ['/recipes'] },
      })
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

      updateNotesSuccess()(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(setState(
        'recipePage.editableNotes',
        false,
      ))
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
      }))
    })
  })

  describe('getRecipesNotesSuccess', () => {
    it('sets the notes in the state', () => {
      const dispatchSpy = jest.fn()
      const notes = 'Some notes'

      getRecipesNotesSuccess({ notes })(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('recipe.notes', notes))
    })
  })
})
