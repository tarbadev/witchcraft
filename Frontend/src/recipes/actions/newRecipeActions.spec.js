import { formInputChange, submitForm, submitFormSuccess } from 'src/recipes/actions/NewRecipeActions'

describe('newRecipeActions', () => {
  describe('formInputChange', function () {
    it('updates the state when called', () => {
      const dispatchSpy = jest.fn()
      const url = 'fakeUrl'

      formInputChange('url', url)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({ type: 'SET_STATE', key: 'newRecipe.forms.url', payload: url })
    })
  })

  describe('submitForm', function () {
    it('submits the form when called', () => {
      const dispatchSpy = jest.fn()
      const url = '/api/recipes/import-from-url'
      const form = {
        url: 'fakeUrl',
      }

      submitForm(url, form)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({
        type: 'FETCH',
        url: url,
        method: 'POST',
        body: form,
        onSuccess: submitFormSuccess,
        onError: undefined,
      })
    })
  })

  describe('submitFormSuccess', function () {
    it('saves the recipeAdded state', () => {
      const dispatchSpy = jest.fn()

      submitFormSuccess()(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({ type: 'SET_STATE', key: 'newRecipe.forms.recipeAdded', payload: true })
    })
  })
})
