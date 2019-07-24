import { getAllCarts } from './CartsActions'
import { fetchAction } from '../../app/WitchcraftMiddleware'

describe('CartsActions', () => {
  it('getAllCarts calls the getAllCarts API endpoint', () => {
    const onSuccessSpy = jest.fn()

    expect(getAllCarts(onSuccessSpy)).toEqual(fetchAction({
      url: '/api/carts',
      method: 'GET',
      onSuccess: onSuccessSpy,
    }))
  })
})
