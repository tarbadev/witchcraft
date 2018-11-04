import React from 'react'
import { shallow } from 'enzyme'

import {CartsPage} from 'app-components/CartsPage'

describe('CartsPage', () => {
  it('redirects to new Cart Page when new Cart Button is clicked', () => {
    const pushSpy = jasmine.createSpy()
    const cartsPage = shallow(<CartsPage history={{push: pushSpy}} carts={[]} />)

    cartsPage.find('.cart-page__new-cart-button').simulate('click')

    expect(pushSpy).toHaveBeenCalledWith('/carts/new')
  })
})
