import { fetchAction } from 'src/WitchcraftMiddleware'
import { setState } from 'src/RootReducer'

export const formInputChange = (key, value) => dispatch =>
  dispatch(setState(`pages.newRecipePage.forms.${key}`, value))

export const submitFormSuccess = () => dispatch =>
  dispatch(setState('pages.newRecipePage.forms.recipeAdded', true))

export const submitForm = (url, form) => dispatch =>
  dispatch(fetchAction({
    url: url,
    method: 'POST',
    body: form,
    onSuccess: submitFormSuccess,
  }))

export const getSupportedDomains = () => dispatch => {
  dispatch(fetchAction({
    url: '/api/recipes/import-from-url/supported',
    method: 'GET',
    onSuccess: getSupportedDomainsSuccess,
  }))
}

export const getSupportedDomainsSuccess = data => dispatch => {
  dispatch(setState('pages.newRecipePage.supportedDomains', data))
}