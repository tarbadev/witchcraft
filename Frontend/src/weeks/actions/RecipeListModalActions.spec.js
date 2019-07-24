import { addExpressRecipe } from './RecipeListModalActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('RecipeListModalActions', () => {
  describe('addExpressRecipe', () => {
    it('calls the api to add an express recipe', () => {
      const onSuccessSpy = jest.fn()
      const recipeName = 'Lasagna'

      expect(addExpressRecipe(recipeName, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/express',
        method: 'POST',
        body: { name: recipeName },
        onSuccess: onSuccessSpy,
      }))
    })
  })
})
