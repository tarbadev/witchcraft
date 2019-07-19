import { addExpressRecipe } from './RecipeListModalActions'
import { fetchAction } from 'src/WitchcraftMiddleware'

describe('RecipeListModalActions', () => {
  describe('addExpressRecipe', () => {
    it('calls the api to add an express recipe', () => {
      const onSuccessSpy = jest.fn()
      const day = 'Monday'
      const meal = 'Lunch'
      const recipeName = 'Lasagna'

      addExpressRecipe(recipeName, day, meal, onSuccessSpy)

      expect(addExpressRecipe(recipeName, day, meal, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/express',
        method: 'POST',
        body: { name: recipeName },
        onSuccess: expect.any(Function),
      }))
    })
  })
})
