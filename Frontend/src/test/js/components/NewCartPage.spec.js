import React from 'react'
import { shallow } from 'enzyme'

import { NewCartPage } from 'app-components/NewCartPage'
import recipeList from 'test-resources/recipeList.json'

describe('NewCartPage', () => {
  it('calls the callback to generate the api', () => {
    const generateCartSpy = jest.fn()
    const form = recipeList.recipes.map(recipe => ({ id: recipe.id, name: recipe.name, selected: false }))
    const newCartPage = shallow(<NewCartPage generateCart={generateCartSpy} form={form} />)

    newCartPage.find('.new-cart-page__generate-button').simulate('click')

    expect(generateCartSpy).toHaveBeenCalledWith(form)
  })
})
