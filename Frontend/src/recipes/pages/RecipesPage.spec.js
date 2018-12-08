import React from 'react'
import { shallow } from 'enzyme'

import { RecipesPage } from './RecipesPage'

describe('RecipesPage', () => {
  it('calls searchRecipe callback on search input change', () => {
    const searchSpy = jest.fn()
    const recipes = shallow(<RecipesPage searchRecipe={searchSpy} />)
    const search = 'search'

    recipes.find('.recipes__search-input').simulate('change', {
      target: {
        value: search,
      },
    })

    expect(searchSpy).toHaveBeenCalledWith(search)
  })
})
