import {
  filterRecipes,
  getAllRecipes,
  getAllRecipesSuccess,
  getFavoriteRecipes,
  getLatestRecipes,
} from 'src/recipes/actions/RecipesActions'

import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

import promisedRecipeList from 'test-resources/recipeList.json'

describe('RecipesActions', () => {
  describe('getAllRecipes', () => {
    it('calls the witchcraft API and sets the state with the list of recipes', () => {
      const dispatchSpy = jest.fn()

      getAllRecipes()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/recipes',
        method: 'GET',
        onSuccess: getAllRecipesSuccess,
      }))
    })
  })

  describe('getAllRecipesSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      getAllRecipesSuccess(promisedRecipeList)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith(setState('allRecipes', promisedRecipeList.recipes))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('recipes', promisedRecipeList.recipes))
    })
  })

  describe('getFavoriteRecipes', () => {
    it('calls the witchcraft API and sets the state with the list of recipes', () => {
      const onSuccessSpy = jest.fn()

      expect(getFavoriteRecipes(onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/favorites',
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('getLatestRecipes', () => {
    it('calls the witchcraft API and sets the state with the list of recipes', () => {
      const onSuccessSpy = jest.fn()

      expect(getLatestRecipes(onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/latest',
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('filterRecipes', () => {
    it('filters the list of recipes based on the input', () => {
      const dispatchSpy = jest.fn()
      const state = {
        allRecipes: [
          { name: 'Recipe test' },
          { name: 'Recipe Two' },
          { name: 'Recipe Three' },
        ],
      }

      filterRecipes('test')(dispatchSpy, () => ({ app: state }))

      expect(dispatchSpy).toHaveBeenCalledWith(setState('recipes', [{ name: 'Recipe test' }]))
    })
  })
})
