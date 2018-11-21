import React from 'react'
import { shallow } from 'enzyme'

import { RecipeTile } from './RecipeTile'

describe('RecipeTile', () => {
  it('calls the onClick callback when clicked on tile', () => {
    const onTileClick = jest.fn()
    const recipe = { name: 'Recipe' }
    const recipeTile = shallow(<RecipeTile onTileClick={onTileClick} recipe={recipe} />)

    recipeTile.find('.recipe-card').simulate('click')

    expect(onTileClick).toHaveBeenCalledWith(recipe)
  })
})
