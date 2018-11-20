import React from 'react'
import { shallow } from 'enzyme'

import { NewCartPage } from 'app-components/NewCartPage'

describe('NewCartPage', () => {
  it('calls the callback to generate the api', () => {
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
})
