import {formInputChange, submitForm} from 'app-actions/NewRecipeActions'
import {history} from 'app-root/Store'
import * as Utils from 'app-root/Utils'

describe('newRecipeActions', () => {
  describe('formInputChange', function() {
    it('updates the state when called', () => {
      const dispatchSpy = jasmine.createSpy()
      const url = 'fakeUrl'

      formInputChange('url', url)(dispatchSpy)

      expect(dispatchSpy).toHaveBeenCalledWith({type: 'SET_STATE', key: 'newRecipe.forms.url', payload: url})
    })
  })

  describe('submitForm', function() {
    it('submits the form when called', async () => {
      const dispatchSpy = jasmine.createSpy()
      spyOn(Utils, 'request').and.returnValue(Promise.resolve({}))
      spyOn(history, 'push')
      const url = '/api/recipes/importFromUrl'
      const form = {
        url: 'fakeUrl'
      }

      await submitForm(url, form)(dispatchSpy)

      expect(Utils.request)
        .toHaveBeenCalledWith({url: url, method: 'post', body: JSON.stringify(form)})

      expect(dispatchSpy).toHaveBeenCalledWith({type: 'SET_STATE', key: 'newRecipe.forms.recipeAdded', payload: true})
    })
  })
})
