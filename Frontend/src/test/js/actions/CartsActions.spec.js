import { getAllCarts, getAllCartsSuccess } from 'app-actions/CartsActions'

describe('CartsActions', () => {
  it('getAllCarts calls the getAllCarts API endpoint', () => {
    const dispatchSpy = jasmine.createSpy()

    getAllCarts()(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'FETCH',
      url: '/api/carts',
      method: 'GET',
      body: undefined,
      onSuccess: getAllCartsSuccess,
      onError: undefined,
    })
  })

  it('getAllCartsSuccess saves the carts to the state', () => {
    const dispatchSpy = jasmine.createSpy()
    const carts = [{
      id: 12,
      recipes: []
    },{
      id: 34,
      recipes: []
    }]

    getAllCartsSuccess(carts)(dispatchSpy)

    expect(dispatchSpy).toHaveBeenCalledWith({
      type: 'SET_STATE',
      key: 'carts',
      payload: carts,
    })
  })
})