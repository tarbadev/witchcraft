import {
  getAllRecipes,
  getAllRecipesSuccess,
  getFavoriteRecipes,
  getFavoriteRecipesSuccess,
  filterRecipes
} from 'app-actions/RecipesActions'

import { fetchAction } from 'app-root/WitchcraftMiddleware'

import promisedRecipeList from 'test-resources/recipeList.json'

describe('RecipesActions', () => {
  describe('getAllRecipes', () => {
    it('calls the witchcraft API and sets the state with the list of recipes', () => {
      const dispatchSpy = jest.fn()

      getAllRecipes()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: '/api/recipes',
        method: 'GET',
        body: undefined,
        onSuccess: getAllRecipesSuccess,
        onError: undefined,
      })
    })
  })

  describe('getAllRecipesSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      getAllRecipesSuccess(promisedRecipeList)(dispatchSpy)
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'allRecipes',
        payload: promisedRecipeList.recipes,
      })
      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'recipes',
        payload: promisedRecipeList.recipes,
      })
    })
  })

  describe('getFavoriteRecipes', () => {
    it('calls the witchcraft API and sets the state with the list of recipes', () => {
      const dispatchSpy = jest.fn()

      getFavoriteRecipes()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: '/api/recipes/favorites',
        method: 'GET',
        onSuccess: getFavoriteRecipesSuccess,
      }))
    })
  })

  describe('getFavoriteRecipesSuccess', () => {
    it('success callback saves recipes in state', () => {
      const dispatchSpy = jest.fn()
      const recipes = [
        { id: 1 },
        { id: 2 }
      ]

      getFavoriteRecipesSuccess(recipes)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'homePage.favoriteRecipes',
        payload: recipes,
      })
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
        ]
      }

      filterRecipes('test')(dispatchSpy, () => state)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'SET_STATE',
        key: 'recipes',
        payload: [{ name: 'Recipe test' }],
      })
    })
  })
})
