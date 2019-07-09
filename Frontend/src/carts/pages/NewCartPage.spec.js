import React from 'react'
import { mount, shallow } from 'enzyme'

import { NewCartPage } from './NewCartPage'

describe('NewCartPage', () => {
  it('calls the callback to generate the cart', () => {
    const generateCartSpy = jest.fn()
    const form = [
      { id: 1, name: 'Recipe 1', selected: true },
      { id: 2, name: 'Recipe 2', selected: true },
      { id: 3, name: 'Recipe 3', selected: false },
      { id: 4, name: 'Recipe 4', selected: true },
    ]
    const recipeIds = form.filter(recipe => recipe.selected).map(recipe => ({ id: recipe.id }))
    const newCartPage = shallow(<NewCartPage generateCart={generateCartSpy} form={form} />)

    newCartPage.find('.new-cart-page__generate-button').simulate('click')

    expect(generateCartSpy).toHaveBeenCalledWith(recipeIds)
  })

  it('calls the callback to when clicking on a recipe', () => {
    const toggleRecipeSwitchSpy = jest.fn()
    const form = [
      { id: 1, name: 'Recipe 1', selected: true },
      { id: 2, name: 'Recipe 2', selected: false },
      { id: 3, name: 'Recipe 3', selected: false },
      { id: 4, name: 'Recipe 4', selected: true },
    ]
    const newCartPage = mount(<NewCartPage toggleRecipeSwitch={toggleRecipeSwitchSpy} form={form} />)

    newCartPage.find('.new-cart-page__switch-recipe-0 input').simulate('change')
    expect(toggleRecipeSwitchSpy).toHaveBeenCalledWith('pages.newCartPage.form.0.selected', false)

    newCartPage.find('.new-cart-page__switch-recipe-1 input').simulate('change')
    expect(toggleRecipeSwitchSpy).toHaveBeenCalledWith('pages.newCartPage.form.1.selected', true)
  })
})
