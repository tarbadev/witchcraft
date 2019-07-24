import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const getAllCarts = onSuccess =>
  fetchAction({
    url: '/api/carts',
    method: 'GET',
    onSuccess: onSuccess,
  })
