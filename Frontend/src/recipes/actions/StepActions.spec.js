import { fetchAction } from '../../app/WitchcraftMiddleware'
import { saveStepNote } from './StepActions'

describe('StepActions', () => {
  describe('saveStepNote', () => {
    it('returns a fetchAction', () => {
      const onSuccessSpy = jest.fn()
      const note = 'Some note'

      expect(saveStepNote(12, 34, note, onSuccessSpy)).toEqual(fetchAction({
        url: '/api/recipes/12/steps/34',
        body: { note },
        method: 'POST',
        onSuccess: onSuccessSpy
      }))
    })
  })
})