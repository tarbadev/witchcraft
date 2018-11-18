import { getAllRecipes, getAllRecipesSuccess, filterRecipes } from 'app-actions/RecipesActions'

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
