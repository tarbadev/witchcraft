import { fetchAction } from 'app-root/WitchcraftMiddleware'
import { push } from 'connected-react-router'

export const createCartSuccess = cart => dispatch => {
  dispatch(push(`#/carts/${cart.id}`))
}

export const createCart = form => dispatch => {
  const body = form.filter(recipe => recipe.selected).map(recipe => ({id: recipe.id}))

  dispatch(fetchAction({
    url: '/api/carts',
    method: 'POST',
    body: body,
    onSuccess: createCartSuccess,
  }))
}
