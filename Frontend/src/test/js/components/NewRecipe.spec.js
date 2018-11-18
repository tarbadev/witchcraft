import React from 'react'
import { shallow } from 'enzyme'

import {NewRecipe} from 'app-components/NewRecipe'

describe('NewRecipe', function () {
  it('calls the callback when input value changes', () => {
    const changeFormInputSpy = jest.fn()
    const newRecipe = shallow(<NewRecipe changeFormInput={changeFormInputSpy} classes={{}} autoUrl={{}} manualUrl={{}} />)
    const url = 'fakeUrl'
    const name = 'name'
    const imageUrl = 'imageUrl'
    const ingredients = 'ingredients'
    const steps = 'steps'

    newRecipe.find('.auto__url').simulate('change', {target: {value: url }})
    newRecipe.find('.manual__name').simulate('change', {target: {value: name }})
    newRecipe.find('.manual__url').simulate('change', {target: {value: url }})
    newRecipe.find('.manual__imageUrl').simulate('change', {target: {value: imageUrl }})
    newRecipe.find('.manual__ingredients').simulate('change', {target: {value: ingredients }})
    newRecipe.find('.manual__steps').simulate('change', {target: {value: steps }})

    expect(changeFormInputSpy).toHaveBeenCalledWith('autoUrl.url', url)
    expect(changeFormInputSpy).toHaveBeenCalledWith('manualUrl.name', name)
    expect(changeFormInputSpy).toHaveBeenCalledWith('manualUrl.url', url)
    expect(changeFormInputSpy).toHaveBeenCalledWith('manualUrl.imageUrl', imageUrl)
    expect(changeFormInputSpy).toHaveBeenCalledWith('manualUrl.ingredients', ingredients)
    expect(changeFormInputSpy).toHaveBeenCalledWith('manualUrl.steps', steps)
  })

  it('calls the callback when submit button is clicked on auto url', () => {
    const submitFormSpy = jest.fn()
    const url = 'fakeUrl'
    const newRecipe = shallow(<NewRecipe submitForm={submitFormSpy} classes={{}} autoUrl={{ url: url }} />)

    newRecipe.find('.auto__submit-button').simulate('click', {})

    expect(submitFormSpy).toHaveBeenCalledWith('/api/recipes/importFromUrl', {url: url})
  })

  it('calls the callback when submit button is clicked on manual url', () => {
    const submitFormSpy = jest.fn()
    const manualUrl = {
      name: 'Mini Goat Cheese Stuffed Potato Appetizers',
      url: 'http://example.com/recipe/32434',
      imageUrl: 'http://example.com/recipe/32434.png',
      ingredients: '2 oz. soft goat cheese (chevre), room temperature\n3 tbsp diced roasted red pepper\n4 pitted Kalamata olives, diced\n1 tbsp minced flat-leaf parsley',
      steps: 'Test\nTest'
    }
    const newRecipe = shallow(<NewRecipe submitForm={submitFormSpy} classes={{}} manualUrl={manualUrl} />)

    newRecipe.find('.manual__submit-button').simulate('click', {})

    expect(submitFormSpy).toHaveBeenCalledWith('/api/recipes/importFromForm', manualUrl)
  })

  it('redirects to /recipes when redirect set to true', () => {
    const setStateSpy = jest.fn()
    const pushSpy = jest.fn()

    shallow(<NewRecipe setState={setStateSpy} history={{ push: pushSpy }} redirect classes={{}} />)

    expect(pushSpy).toHaveBeenCalledWith('/recipes')
    expect(setStateSpy).toHaveBeenCalledWith('newRecipe.forms', { recipeAdded: false, autoUrl: {}, manualUrl: {} })
  })
})
