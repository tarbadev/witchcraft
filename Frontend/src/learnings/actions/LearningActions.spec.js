import { fetchAction } from 'src/app/WitchcraftMiddleware'
import { getIngredientsToValidate, validateIngredient } from 'src/learnings/actions/LearningActions'

describe('LearningActions', () => {
  describe('getIngredientsToValidate', () => {
    it('sends a request to retrieve the ingredients to validate', () => {
      const onSuccessSpy = jest.fn()

      expect(getIngredientsToValidate(onSuccessSpy)).toEqual(fetchAction({
        url: '/api/learning',
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('validateIngredient', () => {
    it('sends a request to save the learned ingredient', () => {
      const onSuccessSpy = jest.fn()
      const onErrorSpy = jest.fn()
      const newIngredient = {
        name: 'salt',
        quantity: 1.0,
        unit: 'tbsp',
        detail: 'to taste',
        language: 'FRENCH',
      }

      expect(validateIngredient(12, newIngredient, onSuccessSpy, onErrorSpy)).toEqual(fetchAction({
        url: '/api/learning/12',
        method: 'PATCH',
        body: newIngredient,
        onSuccess: onSuccessSpy,
        onError: onErrorSpy,
      }))
    })
  })
})