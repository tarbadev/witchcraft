import {
  deleteRecipe,
  getRecipe,
  getRecipeNotes,
  setFavorite,
  updateIngredient,
  updateNotes,
  updateRecipe,
} from './RecipeActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('RecipeActions', () => {
  describe('getRecipe', () => {
    it('calls the witchcraft API', () => {
      const onSuccessSpy = jest.fn()
      const onErrorSpy = jest.fn()

      expect(getRecipe(1, onSuccessSpy, onErrorSpy)).toEqual(fetchAction({
        url: '/api/recipes/1',
        method: 'GET',
        onSuccess: onSuccessSpy,
        onError: onErrorSpy,
      }))
    })
  })

  describe('setFavorite', () => {
    it('calls API and calls onSuccess', () => {
      const onSuccessSpy = jest.fn()

      expect(setFavorite(2, true, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/2',
        method: 'PATCH',
        body: { favorite: true },
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('updateRecipe', () => {
    it('calls the api with the body', () => {
      const onSuccessSpy = jest.fn()
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

      expect(updateRecipe(form, onSuccessSpy)).toEqual(fetchAction({
        url: `/api/recipes/${form.id}/update`,
        method: 'PUT',
        body: form,
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('deleteRecipe', () => {
    it('success callback saves recipes in state', () => {
      const onSuccessSpy = jest.fn()
      const onErrorSpy = jest.fn()

      expect(deleteRecipe(2, onSuccessSpy, onErrorSpy)).toEqual(fetchAction({
        url: '/api/recipes/2',
        method: 'DELETE',
        onSuccess: onSuccessSpy,
        onError: onErrorSpy,
      }))
    })
  })

  describe('updateNotes', () => {
    it('success callback updates the recipe on the backend', () => {
      const onSuccessSpy = jest.fn()
      const notes = 'Keep it going'
      const id = 1

      expect(updateNotes(id, notes, onSuccessSpy)).toEqual(fetchAction({
        url: `/api/recipes/${id}/notes`,
        method: 'POST',
        body: { recipeId: id, notes },
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('getRecipeNotes', () => {
    it('gets the notes for a given recipe id', () => {
      const onSuccessSpy = jest.fn()
      const id = 1

      expect(getRecipeNotes(id, onSuccessSpy)).toEqual(fetchAction({
        url: `/api/recipes/${id}/notes`,
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('updateIngredient', () => {
    it('calls API and calls onSuccess', () => {
      const onSuccessSpy = jest.fn()
      const ingredient = {
        id: 45,
        name: 'Ingredient 1',
        unit: 'tbsp',
        quantity: 12,
      }

      expect(updateIngredient(2, ingredient, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/2/ingredients/45',
        method: 'PUT',
        body: ingredient,
        onSuccess: onSuccessSpy,
      }))
    })
  })
})
