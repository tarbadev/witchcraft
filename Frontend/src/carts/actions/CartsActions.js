import { fetchAction } from 'src/WitchcraftMiddleware'

export const getAllCarts = onSuccess =>
  fetchAction({
    url: '/api/carts',
    method: 'GET',
    onSuccess: onSuccess,
  })
