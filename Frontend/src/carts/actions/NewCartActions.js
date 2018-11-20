import { fetchAction } from 'src/WitchcraftMiddleware'
import { push } from 'connected-react-router'

export const createCartSuccess = cart => dispatch => {
  dispatch(push(`#/carts/${cart.id}`))
}

export const createCart = recipeIds => dispatch => {
  dispatch(fetchAction({
    url: '/api/carts',
    method: 'POST',
    body: recipeIds,
    onSuccess: createCartSuccess,
  }))
}
