import { filterRecipes, getAllRecipes, getFavoriteRecipes, getLatestRecipes } from 'src/recipes/actions/RecipesActions'

import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('RecipesActions', () => {
  describe('getAllRecipes', () => {
    it('calls the witchcraft API', () => {
      const onSuccessSpy = jest.fn()

      expect(getAllRecipes(onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes',
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
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
      const allRecipes = [
        { name: 'Recipe test' },
        { name: 'Recipe Two' },
        { name: 'Recipe Three' },
      ]

      filterRecipes(allRecipes, 'test')

      expect(filterRecipes(allRecipes, 'test')).toEqual([{ name: 'Recipe test' }])
    })
  })
})
