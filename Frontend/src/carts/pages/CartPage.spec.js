import React from 'react'
import { shallow } from 'enzyme'
import { CartPage } from './CartPage'

describe('CartPage', () => {
  it('calls the onItemClick callback when clicking on it', () => {
    const onItemClickSpy = jest.fn()
    const cart = {
      id: 2,
      items: [{
        id: 21,
        name: 'Some ingredient',
        enabled: true,
      }],
    }
    const cartsPage = shallow(<CartPage cart={cart} onItemClick={onItemClickSpy} />)

    cartsPage.find('.cart-page__ingredient').simulate('click')

    expect(onItemClickSpy).toHaveBeenCalledWith(2, 21, false)
  })
})
