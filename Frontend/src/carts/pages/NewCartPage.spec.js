import React from 'react'
import { mount } from 'enzyme'

import { NewCartPage } from './NewCartPage'

describe('NewCartPage', () => {
  it('calls generateCart to when clicking on the generate cart button', () => {
    const generateCartSpy = jest.fn()
    const recipes = [
      { id: 1, name: 'Recipe 1' },
      { id: 2, name: 'Recipe 2' },
      { id: 3, name: 'Recipe 3' },
      { id: 4, name: 'Recipe 4' },
    ]
    const recipeIds = [1, 3]
    const newCartPage = mount(<NewCartPage generateCart={generateCartSpy} recipes={recipes} />)

    newCartPage.find('.new-cart-page__switch-recipe-0 input').simulate('change')
    newCartPage.find('.new-cart-page__switch-recipe-2 input').simulate('change')

    newCartPage.find('.new-cart-page__generate-button button').simulate('click')

    expect(generateCartSpy).toHaveBeenCalledWith(recipeIds)
  })
})
