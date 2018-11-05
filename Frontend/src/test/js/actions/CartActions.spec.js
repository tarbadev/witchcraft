import { getCartSuccess, getCart } from 'app-actions/CartActions'

describe('CartActions', () => {
  it('getCart sends a request to retrieve a cart', () => {
    const dispatchSpy = jasmine.createSpy()
    const cartRequest = { id: 1 }

    getCart(cartRequest.id)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'FETCH',
      url: `/api/carts/${cartRequest.id}`,
      method: 'GET',
      body: undefined,
      onSuccess: getCartSuccess,
      onError: undefined,
    })
  })

  it('getCartSuccess sends a request to retrieve a cart', () => {
    const dispatchSpy = jasmine.createSpy()
    const cart = { id: 1, recipes: [] }

    getCartSuccess(cart)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'SET_STATE',
      key: 'cart',
      payload: cart,
    })
  })
})
