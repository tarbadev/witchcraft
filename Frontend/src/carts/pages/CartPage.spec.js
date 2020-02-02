import React from 'react'
import { mount, shallow } from 'enzyme'
import { CartPage, CartPageContainer } from './CartPage'
import { mockAppContext } from 'src/testUtils'
import * as CartActions from 'src/carts/actions/CartActions'
import { BrowserRouter } from 'react-router-dom'
import { Header } from 'src/app/components/Header'

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
    let cartPageContainer
    let context
    let pushSpy
    const promisedCart = { id: 12 }
    const id = promisedCart.id

    beforeEach(() => {
      context = mockAppContext()
      pushSpy = jest.fn()

      jest
        .spyOn(CartActions, 'getCart')
        .mockImplementation((id, onSuccess) => onSuccess(promisedCart))

      mount(<CartPageContainer match={{ params: { id } }} history={{ push: pushSpy }} />)

      const headerConfig = context.setHeaderConfig.mock.calls[1][0]
      mockAppContext({ headerConfig })

      cartPageContainer = mount(<BrowserRouter>
        <Header />
        <CartPageContainer match={{ params: { id } }} history={{ push: pushSpy }} />
      </BrowserRouter>)
    })

    it('dispatches deleteCart when Delete button clicked', () => {
      cartPageContainer.find('[data-delete-cart-button]').at(0).simulate('click')

      expect(context.dispatch).toHaveBeenLastCalledWith(CartActions.deleteCart(id, expect.any(Function), expect.any(Function)))
    })

    it('redirects to carts page when delete succeeds', () => {
      jest
        .spyOn(CartActions, 'deleteCart')
        .mockImplementation((id, onSuccess) => onSuccess())

      cartPageContainer.find('[data-delete-cart-button]').at(0).simulate('click')

      expect(pushSpy).toHaveBeenLastCalledWith('/carts')
    })
  })
})