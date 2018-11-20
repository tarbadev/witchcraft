import { fetchAction } from 'app-root/WitchcraftMiddleware'
import { setState } from 'app-root/RootReducer'

export const formInputChange = (key, value) => dispatch =>
  dispatch(setState(`newRecipe.forms.${key}`, value))

export const submitFormSuccess = () => dispatch =>
  dispatch(setState('newRecipe.forms.recipeAdded', true))

export const submitForm = (url, form) => dispatch =>
  dispatch(fetchAction({
    url: url,
    method: 'POST',
    body: form,
    onSuccess: submitFormSuccess,
  }))
