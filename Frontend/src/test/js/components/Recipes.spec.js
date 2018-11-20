import React from 'react'
import {shallow} from 'enzyme'

import { Recipes } from 'app-components/Recipes'

describe('Recipes', () => {
  it('calls searchRecipe callback on search input change', () => {
    const searchSpy = jest.fn()
    const recipes = shallow(<Recipes searchRecipe={searchSpy} />)
    const search = 'search'

    recipes.find('.recipes__search-input').simulate('change', {
      target: {
        value: search
      }
    })

    expect(searchSpy).toHaveBeenCalledWith(search)
  })
})
