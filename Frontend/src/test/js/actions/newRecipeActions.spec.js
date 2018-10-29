import { urlInputChange, submitUrlForm } from 'app-actions/newRecipeActions'
import { history } from 'app-root/Store'
import * as Utils from 'app-root/Utils'

describe("urlInputChange", function () {
  it('updates the state when called', () => {
    const dispatchSpy = jasmine.createSpy()
    const url = "fakeUrl"

    urlInputChange(url)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({ type: 'SET_STATE', key: 'newRecipe.form.url', payload: url })
  })
})

describe("submitUrlForm", function () {
  it('submits the form when called', async () => {
    const dispatchSpy = jasmine.createSpy()
    spyOn(Utils, 'request').and.returnValue(Promise.resolve({}))
    spyOn(history, 'push');
    const url = { url: 'fakeUrl' }

    await submitUrlForm(url)(dispatchSpy)

    expect(Utils.request).toHaveBeenCalledWith({
      url: '/api/recipes/importFromUrl',
      method: 'post',
      body: JSON.stringify(url)
    })

    expect(dispatchSpy).toHaveBeenCalledWith({ type: 'SET_STATE', key: 'newRecipe.form.recipeAdded', payload: true })
  })
})
