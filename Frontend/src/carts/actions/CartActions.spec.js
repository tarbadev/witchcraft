import { getCart, getCartSuccess, toggleItem, toggleItemSuccess } from './CartActions'
import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

describe('CartActions', () => {
  it('getCart sends a request to retrieve a cart', () => {
    const dispatchSpy = jest.fn()
    const cartRequest = { id: 1 }

    getCart(cartRequest.id)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
      url: `/api/carts/${cartRequest.id}`,
      method: 'GET',
      onSuccess: getCartSuccess,
    }))
  })

  it('getCartSuccess sends a request to retrieve a cart', () => {
    const dispatchSpy = jest.fn()
    const cart = { id: 1, recipes: [] }

    getCartSuccess(cart)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'SET_STATE',
      key: 'cart',
      payload: cart,
    })
  })

  it('toggleItem sends a request to update the given item', () => {
    const dispatchSpy = jest.fn()

    toggleItem(2, 32, true)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
      url: '/api/carts/2/items/32',
      method: 'PUT',
      body: { enabled: true },
      onSuccess: toggleItemSuccess,
    }))
  })

  it('toggleItemSuccess saves the item in the state', () => {
    const dispatchSpy = jest.fn()
    const getStateSpy = jest.fn(() => ({
      app: {
        cart: {
          items: [
            { id: 1 },
            { id: 3 },
            { id: 6 },
          ],
        },
      },
    }))
    const data = {
      name: 'Ingredient',
      id: 3,
      enabled: true,
    }

    toggleItemSuccess(data)(dispatchSpy, getStateSpy)

    expect(dispatchSpy).toHaveBeenCalledWith(setState('cart.items.1', data))
  })
})
