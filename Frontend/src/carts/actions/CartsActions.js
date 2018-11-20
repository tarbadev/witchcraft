import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

export const getAllCartsSuccess = data => dispatch => {
  dispatch(setState('carts', data))
}

export const getAllCarts = () => dispatch => {
  dispatch(fetchAction({
    url: '/api/carts',
    method: 'GET',
    onSuccess: getAllCartsSuccess,
  }))
}
