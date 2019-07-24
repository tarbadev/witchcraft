import { getSupportedDomains, submitForm } from './NewRecipeActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('newRecipeActions', () => {
  describe('submitForm', function () {
    it('submits the form when called', () => {
      const onSuccessSpy = jest.fn()
      const url = '/api/recipes/import-from-url'
      const form = {
        url: 'fakeUrl',
      }

      expect(submitForm(url, form, onSuccessSpy)).toEqual(fetchAction({
        url: url,
        method: 'POST',
        body: form,
        onSuccess: onSuccessSpy,
      }))
    })
  })

  describe('getSupportedDomains', function () {
    it('fetches the supported actions', () => {
      const onSuccessSpy = jest.fn()
      const url = '/api/recipes/import-from-url/supported'

      expect(getSupportedDomains(onSuccessSpy)).toEqual(fetchAction({
        url: url,
        method: 'GET',
        onSuccess: onSuccessSpy,
      }))
    })
  })
})
