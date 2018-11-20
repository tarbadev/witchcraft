import { fetchAction } from 'app-root/WitchcraftMiddleware'
import { setState } from 'app-root/RootReducer'

export const getCartSuccess = cart => dispatch => {
  dispatch(setState('cart', cart))
}

export const getCart = id => dispatch => {
  dispatch(fetchAction({
    type: 'FETCH',
    url: `/api/carts/${id}`,
    method: 'GET',
    onSuccess: getCartSuccess,
  }))
}
