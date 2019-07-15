import { fetchAction } from 'src/WitchcraftMiddleware'

export const createCart = (recipeIds, onSuccess) =>
  fetchAction({
    url: '/api/carts',
    method: 'POST',
    body: recipeIds.map(id => ({ id })),
    onSuccess,
  })