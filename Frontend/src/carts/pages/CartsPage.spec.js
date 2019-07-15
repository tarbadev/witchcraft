import React from 'react'
import { mount } from 'enzyme'

import { CartsPageContainer } from './CartsPage'
import { getAllCarts } from '../actions/CartsActions'
import { mockAppContext } from 'src/testUtils'

describe('CartsPageContainer', () => {
  it('redirects to new Cart Page when new Cart Button is clicked', () => {
    mockAppContext()
    const pushSpy = jest.fn()
    const cartsPage = mount(<CartsPageContainer history={{ push: pushSpy }} />)

    cartsPage.find('.cart-page__new-cart-button button').simulate('click')

    expect(pushSpy).toHaveBeenCalledWith('/carts/new')
  })

  it('loads the list of carts when mounting', () => {
    const context = mockAppContext()

    mount(<CartsPageContainer />)

    expect(context.dispatch).toHaveBeenNthCalledWith(1, getAllCarts(expect.any(Function)))
  })
})
