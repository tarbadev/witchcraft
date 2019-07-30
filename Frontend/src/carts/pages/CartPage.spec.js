import React from 'react'
import { mount, shallow } from 'enzyme'
import { CartPage, CartPageContainer } from './CartPage'
import { mockAppContext } from 'src/testUtils'
import * as CartActions from 'src/carts/actions/CartActions'

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
    const context = mockAppContext()
    const id = 2

    const cartPageContainer = mount(<CartPageContainer match={{ params: { id } }} />)

    expect(cartPageContainer).toBeDefined()
    expect(context.dispatch).toHaveBeenNthCalledWith(1, CartActions.getCart(id, expect.any(Function)))
  })

  describe('delete', () => {
    it('dispatches deleteCart when Delete button clicked', () => {
      const context = mockAppContext()
      const promisedCart = { id: 12 }
      const id = promisedCart.id

      jest
        .spyOn(CartActions, 'getCart')
        .mockImplementation((id, onSuccess) => onSuccess(promisedCart))

      const cartPageContainer = mount(<CartPageContainer match={{ params: { id } }} />)
      cartPageContainer.find('.cart-page__delete-button button').simulate('click')

      expect(context.dispatch).toHaveBeenLastCalledWith(CartActions.deleteCart(id, expect.any(Function), expect.any(Function)))
    })

    it('displays circular progress when delete button is clicked', () => {
      mockAppContext()
      const promisedCart = { id: 12 }
      const id = promisedCart.id

      jest
        .spyOn(CartActions, 'getCart')
        .mockImplementation((id, onSuccess) => onSuccess(promisedCart))

      const cartPageContainer = mount(<CartPageContainer match={{ params: { id } }} />)

      expect(cartPageContainer.find('.circularProgress')).toHaveLength(0)

      cartPageContainer.find('.cart-page__delete-button button').simulate('click')

      expect(cartPageContainer.find('.circularProgress')).toHaveLength(3)
    })

    it('Hides CircularProgress call is successful', () => {
      mockAppContext()
      const promisedCart = { id: 12 }
      const id = promisedCart.id

      jest
        .spyOn(CartActions, 'getCart')
        .mockImplementation((id, onSuccess) => onSuccess(promisedCart))

      const cartPageContainer = mount(
        <CartPageContainer
          match={{ params: { id } }}
          history={{ push: jest.fn() }} />)

      expect(cartPageContainer.find('.circularProgress')).toHaveLength(0)

      jest
        .spyOn(CartActions, 'deleteCart')
        .mockImplementation((id, onSuccess) => onSuccess())

      cartPageContainer.find('.cart-page__delete-button button').simulate('click')
      expect(cartPageContainer.find('.circularProgress')).toHaveLength(0)
    })

    it('redirects to carts page when delete succeeds', () => {
      mockAppContext()
      const promisedCart = { id: 12 }
      const id = promisedCart.id
      const pushSpy = jest.fn()

      jest
        .spyOn(CartActions, 'getCart')
        .mockImplementation((id, onSuccess) => onSuccess(promisedCart))

      jest
        .spyOn(CartActions, 'deleteCart')
        .mockImplementation((id, onSuccess) => onSuccess())

      const cartPageContainer = mount(<CartPageContainer match={{ params: { id } }} history={{ push: pushSpy }} />)
      cartPageContainer.find('.cart-page__delete-button button').simulate('click')

      expect(pushSpy).toHaveBeenLastCalledWith('/carts')
    })
  })
})