import { addExpressRecipe, setRecipe } from './RecipeListModalActions'
import { setState } from 'src/RootReducer'
import { fetchAction } from '../../WitchcraftMiddleware'

describe('RecipeListModalActions', () => {
  describe('setRecipe', () => {
    it('sets the meal on a given day based on the recipe and closes modal', () => {
      const dispatchSpy = jest.fn()
      const day = 'Monday'
      const meal = 'Lunch'
      const recipe = { name: 'recipeName' }
      const newModal = {
        isModalOpen: false,
        day: '',
        meal: '',
        currentRecipe: 0,
        displayExpressRecipeForm: false,
      }

      setRecipe(recipe, day, meal)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('week.days.0.lunch', recipe))
      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.weekPage.modal', newModal))
    })
  })

  describe('addExpressRecipe', () => {
    it('calls the api to add an express recipe', () => {
      const dispatchSpy = jest.fn()
      const day = 'Monday'
      const meal = 'Lunch'
      const recipeName = 'Lasagna'

      addExpressRecipe(recipeName, day, meal)(dispatchSpy)

      const actual = dispatchSpy.mock.calls[0][0]

      let expectedFetchAction = fetchAction({
        url: '/api/recipes/express',
        method: 'POST',
        body: { name: recipeName },
        onSuccess: (recipe) => setRecipe(recipe, day, meal)
      })
      expect(actual.url).toEqual(expectedFetchAction.url)
      expect(actual.method).toEqual(expectedFetchAction.method)
      expect(actual.body).toEqual(expectedFetchAction.body)
    })
  })
})
