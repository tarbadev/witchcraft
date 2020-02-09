import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const getIngredientsToValidate = onSuccess =>
  fetchAction({
    url: '/api/learning',
    method: 'GET',
    onSuccess
  })
