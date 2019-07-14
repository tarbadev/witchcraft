import React from 'react'
import { mount, shallow } from 'enzyme'
import { CartPage, CartPageContainer } from './CartPage'
import { initialState } from '../../RootReducer'
import * as StoreProvider from '../../StoreProvider'
import { getCart } from '../actions/CartActions'

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

describe('CartPageContainer', function () {
  it('renders without crashing and loads the cart', () => {
    const context = { state: initialState, dispatch: jest.fn() }
    const id = 2

    jest
      .spyOn(StoreProvider, 'useAppContext')
      .mockImplementation(() => context)

    const home = mount(<CartPageContainer match={{ params: { id } }} />)

    expect(home).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, getCart(id, expect.any(Function)))
  })
})