import { fetchAction } from '../../app/WitchcraftMiddleware'

export const addExpressRecipe = (recipeName, onSuccess) =>
  fetchAction({
    url: '/api/recipes/express',
    method: 'POST',
    body: { name: recipeName },
    onSuccess,
  })