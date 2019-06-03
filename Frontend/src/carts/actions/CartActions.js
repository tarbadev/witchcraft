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

export const toggleItem = (cartId, ingredientId, enabled) => dispatch => {
  dispatch(fetchAction({
    url: `/api/carts/${cartId}/items/${ingredientId}`,
    method: 'PUT',
    body: { enabled },
    onSuccess: toggleItemSuccess
  }))
}

export const toggleItemSuccess = data => (dispatch, getState) => {
  const index = findWithAttr(getState().cart.items, 'id', data.id)
  dispatch(setState(`cart.items.${index}`, data))
}

const findWithAttr = (array, attr, value) => {
  for(let i = 0; i < array.length; i += 1) {
    if(array[i][attr] === value) {
      return i
    }
  }
  return -1
}