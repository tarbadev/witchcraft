import React from 'react'
import { mount } from 'enzyme'

import { NewRecipePageContainer } from './NewRecipePage'
import * as NewRecipeActions from '../actions/NewRecipeActions'
import { mockAppContext } from 'src/testUtils'

describe('NewRecipePageContainer', () => {
  describe('when submit button is clicked on auto url', () => {
    it('calls submitForm', () => {
      const context = mockAppContext()
      const url = 'fakeUrl'
      const newRecipe = mount(<NewRecipePageContainer />)

      newRecipe.find('.auto__url input').simulate('change', { target: { value: url } })
      newRecipe.find('[data-tag="auto-url-submit-button"] button').simulate('click', {})

      expect(context.dispatch)
        .toHaveBeenCalledWith(NewRecipeActions.submitForm('/api/recipes/import-from-url',
          { url: url },
          expect.any(Function)))
    })

    it('displays a circular progress', () => {
      mockAppContext()
      const url = 'fakeUrl'
      const newRecipe = mount(<NewRecipePageContainer />)

      expect(newRecipe.find('[data-tag="auto-url-loading"]')).toHaveLength(0)

      newRecipe.find('.auto__url input').simulate('change', { target: { value: url } })
      newRecipe.find('[data-tag="auto-url-submit-button"] button').simulate('click', {})

      expect(newRecipe.find('[data-tag="auto-url-loading"]').length).toBeGreaterThanOrEqual(1)
    })

    it('hides a circular progress when form is submitted and pushes to recipes page', () => {
      mockAppContext()
      const pushSpy = jest.fn()
      const newRecipe = mount(<NewRecipePageContainer history={{ push: pushSpy }} />)

      jest
        .spyOn(NewRecipeActions, 'submitForm')
        .mockImplementation((url, form, onSuccess) => onSuccess())

      newRecipe.find('[data-tag="auto-url-submit-button"] button').simulate('click', {})

      expect(newRecipe.find('[data-tag="auto-url-loading"]')).toHaveLength(0)
      expect(pushSpy).toHaveBeenCalledWith('/recipes')
    })
  })

  describe('when submit button is clicked on manual url', () => {
    it('calls submitForm', () => {
      const context = mockAppContext()
      const manualForm = {
        name: 'Mini Goat Cheese Stuffed Potato Appetizers',
        url: 'http://example.com/recipe/32434',
        imageUrl: 'http://example.com/recipe/32434.png',
        ingredients: '2 oz. soft goat cheese (chevre), room temperature\n3 tbsp diced roasted red pepper\n4 pitted Kalamata olives, diced\n1 tbsp minced flat-leaf parsley',
        steps: 'Test\nTest',
        portions: '4',
      }
      const newRecipe = mount(<NewRecipePageContainer />)

      newRecipe.find('.manual__name input').simulate('change', { target: { value: manualForm.name } })
      newRecipe.find('.manual__url input').simulate('change', { target: { value: manualForm.url } })
      newRecipe.find('.manual__image-url input').simulate('change', { target: { value: manualForm.imageUrl } })
      newRecipe.find('.manual__ingredients textarea[name="ingredients"]')
        .simulate('change', { target: { value: manualForm.ingredients } })
      newRecipe.find('.manual__steps textarea[name="steps"]')
        .simulate('change', { target: { value: manualForm.steps } })
      newRecipe.find('.manual__portions input').simulate('change', { target: { value: manualForm.portions } })
      newRecipe.find('[data-tag="manual-url-submit-button"] button').simulate('click', {})

      expect(context.dispatch)
        .toHaveBeenCalledWith(NewRecipeActions.submitForm('/api/recipes/import-from-form',
          manualForm,
          expect.any(Function)))
    })

    it('displays a circular progress', () => {
      mockAppContext()
      const newRecipe = mount(<NewRecipePageContainer />)

      expect(newRecipe.find('[data-manual-url-loading]')).toHaveLength(0)

      newRecipe.find('[data-tag="manual-url-submit-button"] button').simulate('click', {})

      expect(newRecipe.find('[data-tag="manual-url-loading"]').length).toBeGreaterThanOrEqual(1)
    })

    it('hides a circular progress when form is submitted and pushes to recipes page', () => {
      mockAppContext()
      const pushSpy = jest.fn()
      const newRecipe = mount(<NewRecipePageContainer history={{ push: pushSpy }} />)

      jest
        .spyOn(NewRecipeActions, 'submitForm')
        .mockImplementation((url, form, onSuccess) => onSuccess())

      newRecipe.find('[data-tag="manual-url-submit-button"] button').simulate('click', {})

      expect(newRecipe.find('[data-manual-url-loading]')).toHaveLength(0)
      expect(pushSpy).toHaveBeenCalledWith('/recipes')
    })
  })

  it('loads the supported domains', () => {
    const context = mockAppContext()

    mount(<NewRecipePageContainer />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, NewRecipeActions.getSupportedDomains(expect.any(Function)))
  })
})
