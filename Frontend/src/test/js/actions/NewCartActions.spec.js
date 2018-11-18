import { createCart, createCartSuccess } from 'app-actions/NewCartActions'

describe('NewCartActions', () => {
  it('createCart sends a request to create the cart', () => {
    const dispatchSpy = jest.fn()
    const form = [
      {
        id: 1,
        name: 'test',
        selected: true
      },
      {
        id: 2,
        name: 'test',
        selected: false
      }
    ]

    createCart(form)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'FETCH',
      url: '/api/carts',
      method: 'POST',
      body: [{id:1}],
      onSuccess: createCartSuccess,
      onError: undefined,
    })
  })

  it('createCartSuccess redirects to generated cart', () => {
    const dispatchSpy = jest.fn()
    const cart = {id: 1}

    createCartSuccess(cart)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: '@@router/CALL_HISTORY_METHOD',
      payload: { method: 'push', args: [ `#/carts/${cart.id}` ] },
    })
  })
})
