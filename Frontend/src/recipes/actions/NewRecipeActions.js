import { fetchAction } from 'src/app/WitchcraftMiddleware'

export const submitForm = (url, form, onSuccess) =>
  fetchAction({
    url: url,
    method: 'POST',
    body: form,
    onSuccess,
  })

export const getSupportedDomains = onSuccess =>
  fetchAction({
    url: '/api/recipes/import-from-url/supported',
    method: 'GET',
    onSuccess,
  })