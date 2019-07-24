import { getCart, toggleItem } from './CartActions'
import { fetchAction } from 'src/app/WitchcraftMiddleware'

describe('CartActions', () => {
  it('getCart sends a request to retrieve a cart', () => {
    const onSuccessSpy = jest.fn()
    const cartRequest = { id: 1 }

    expect(getCart(cartRequest.id, onSuccessSpy)).toEqual(fetchAction({
      url: `/api/carts/${cartRequest.id}`,
      method: 'GET',
      onSuccess: onSuccessSpy,
    }))
  })

  it('toggleItem sends a request to update the given item', () => {
    const onSuccessSpy = jest.fn()

    expect(toggleItem(2, 32, true, onSuccessSpy)).toEqual(fetchAction({
      url: '/api/carts/2/items/32',
      method: 'PUT',
      body: { enabled: true },
      onSuccess: onSuccessSpy,
    }))
  })
})
