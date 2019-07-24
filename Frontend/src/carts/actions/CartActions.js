import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const getCart = (id, onSuccess) =>
  fetchAction({
    type: 'FETCH',
    url: `/api/carts/${id}`,
    method: 'GET',
    onSuccess,
  })


export const toggleItem = (cartId, ingredientId, enabled, onSuccess) =>
  fetchAction({
    url: `/api/carts/${cartId}/items/${ingredientId}`,
    method: 'PUT',
    body: { enabled },
    onSuccess,
  })

export const findWithAttr = (array, attr, value) => {
  for (let i = 0; i < array.length; i += 1) {
    if (array[i][attr] === value) {
      return i
    }
  }
  return -1
}