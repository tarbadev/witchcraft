import { getSupportedDomains, getSupportedDomainsSuccess, submitForm, submitFormSuccess } from './NewRecipeActions'
import { setState } from 'src/RootReducer'
import { fetchAction } from 'src/WitchcraftMiddleware'

describe('newRecipeActions', () => {
  describe('submitForm', function () {
    it('submits the form when called', () => {
      const dispatchSpy = jest.fn()
      const url = '/api/recipes/import-from-url'
      const form = {
        url: 'fakeUrl',
      }

      submitForm(url, form)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: url,
        method: 'POST',
        body: form,
        onSuccess: submitFormSuccess,
      }))
    })
  })

  describe('submitFormSuccess', function () {
    it('saves the recipeAdded state', () => {
      const dispatchSpy = jest.fn()

      submitFormSuccess()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState('pages.newRecipePage.forms.recipeAdded', true))
    })
  })

  describe('getSupportedDomains', function () {
    it('fetches the supported actions', () => {
      const dispatchSpy = jest.fn()
      const url = '/api/recipes/import-from-url/supported'

      getSupportedDomains()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
        url: url,
        method: 'GET',
        onSuccess: getSupportedDomainsSuccess,
      }))
    })
  })

  describe('getSupportedDomainsSuccess', function () {
    it('stores the supported domains in the state', () => {
      const dispatchSpy = jest.fn()
      const data = [
        {
          name: 'Some Vendor',
          url: 'www.some.vendor.example.com/recipes',
          imgUrl: 'www.some.vendor.example.com/logo.png',
        },
      ]

      getSupportedDomainsSuccess(data)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith(setState(
        'pages.newRecipePage.supportedDomains',
        data,
      ))
    })
  })
})
