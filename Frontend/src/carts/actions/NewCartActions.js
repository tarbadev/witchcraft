import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const createCart = (recipeIds, onSuccess) =>
  fetchAction({
    url: '/api/carts',
    method: 'POST',
    body: recipeIds.map(id => ({ id })),
    onSuccess,
  })