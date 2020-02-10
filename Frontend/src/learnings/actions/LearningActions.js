import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const getIngredientsToValidate = onSuccess =>
  fetchAction({
    url: '/api/learning',
    method: 'GET',
    onSuccess,
  })

export const validateIngredient = (id, newIngredient, onSuccess, onError) => {
  return fetchAction({
    url: `/api/learning/${id}`,
    method: 'PATCH',
    body: newIngredient,
    onSuccess,
    onError,
  })
}
