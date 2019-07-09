import { fetchAction } from 'src/WitchcraftMiddleware'
import { createCart, createCartSuccess } from './NewCartActions'

describe('NewCartActions', () => {
  it('createCart sends a request to create the cart', () => {
    const dispatchSpy = jest.fn()
    const recipeIds = [1, 2]
    const recipeIdsForm = [
      { id: 1 },
      { id: 2 },
    ]

    createCart(recipeIds)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith(fetchAction({
      url: '/api/carts',
      method: 'POST',
      body: recipeIdsForm,
      onSuccess: createCartSuccess,
    }))
  })

  it('createCartSuccess redirects to generated cart', () => {
    const dispatchSpy = jest.fn()
    const cart = { id: 1 }

    createCartSuccess(cart)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: '@@router/CALL_HISTORY_METHOD',
      payload: { method: 'push', args: [`/carts/${cart.id}`] },
    })
  })
})
