import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

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
