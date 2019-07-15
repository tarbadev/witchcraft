import React from 'react'
import { mount } from 'enzyme'

import { NewRecipePageContainer } from './NewRecipePage'
import { initialState } from 'src/RootReducer'
import * as StoreProvider from 'src/StoreProvider'
import { getSupportedDomains } from '../actions/NewRecipeActions'

describe('NewRecipePageContainer', function () {
  it('calls submitForm when submit button is clicked on auto url', () => {
    const submitFormSpy = jest.fn()
    const url = 'fakeUrl'
    const newRecipe = mount(<NewRecipePageContainer submitForm={submitFormSpy} classes={{}} />)

    newRecipe.find('.auto__url input').simulate('change', { target: { value: url } })
    newRecipe.find('.auto__submit-button button').simulate('click', {})

    expect(submitFormSpy).toHaveBeenCalledWith('/api/recipes/import-from-url', { url: url }, expect.any(Function))
  })

  it('calls submitForm when submit button is clicked on manual url', () => {
    const submitFormSpy = jest.fn()
    const manualForm = {
      name: 'Mini Goat Cheese Stuffed Potato Appetizers',
      url: 'http://example.com/recipe/32434',
      imageUrl: 'http://example.com/recipe/32434.png',
      ingredients: '2 oz. soft goat cheese (chevre), room temperature\n3 tbsp diced roasted red pepper\n4 pitted Kalamata olives, diced\n1 tbsp minced flat-leaf parsley',
      steps: 'Test\nTest',
      portions: '4'
    }
    const newRecipe = mount(<NewRecipePageContainer submitForm={submitFormSpy} classes={{}} />)

    newRecipe.find('.manual__name input').simulate('change', { target: { value: manualForm.name } })
    newRecipe.find('.manual__url input').simulate('change', { target: { value: manualForm.url } })
    newRecipe.find('.manual__image-url input').simulate('change', { target: { value: manualForm.imageUrl } })
    newRecipe.find('.manual__ingredients textarea[name="ingredients"]').simulate('change', { target: { value: manualForm.ingredients } })
    newRecipe.find('.manual__steps textarea[name="steps"]').simulate('change', { target: { value: manualForm.steps } })
    newRecipe.find('.manual__portions input').simulate('change', { target: { value: manualForm.portions } })
    newRecipe.find('.manual__submit-button button').simulate('click', {})

    expect(submitFormSpy).toHaveBeenCalledWith('/api/recipes/import-from-form', manualForm, expect.any(Function))
  })

  it('loads the supported domains', () => {
    const context = { state: initialState, dispatch: jest.fn() }

    jest
      .spyOn(StoreProvider, 'useAppContext')
      .mockImplementation(() => context)

    mount(<NewRecipePageContainer />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, getSupportedDomains(expect.any(Function)))
  })
})
