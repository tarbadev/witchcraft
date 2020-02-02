import React from 'react'
import { mount } from 'enzyme'

import { CartsPageContainer } from './CartsPage'
import { getAllCarts } from '../actions/CartsActions'
import { mockAppContext } from 'src/testUtils'

describe('CartsPageContainer', () => {
  it('loads the list of carts when mounting', () => {
    const context = mockAppContext()

    mount(<CartsPageContainer />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, getAllCarts(expect.any(Function)))
  })
})
