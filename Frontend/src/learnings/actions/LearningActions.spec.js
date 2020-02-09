import { fetchAction } from 'src/app/WitchcraftMiddleware'
import { getIngredientsToValidate } from 'src/learnings/actions/LearningActions'

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
})