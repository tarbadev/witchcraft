import { fetchAction } from 'src/app/WitchcraftMiddleware'
import { createCart } from './NewCartActions'

describe('NewCartActions', () => {
  it('createCart sends a request to create the cart', () => {
    const onSuccessSpy = jest.fn()
    const recipeIds = [1, 2]
    const recipeIdsForm = [
      { id: 1 },
      { id: 2 },
    ]

    expect(createCart(recipeIds, onSuccessSpy)).toEqual(fetchAction({
      url: '/api/carts',
      method: 'POST',
      body: recipeIdsForm,
      onSuccess: onSuccessSpy,
    }))
  })
})
