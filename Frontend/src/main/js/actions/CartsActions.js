import { fetchAction } from 'app-root/WitchcraftMiddleware'
import { setState } from 'app-root/RootReducer'

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
