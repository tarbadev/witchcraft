import { request } from './Utils'

export const FETCH = 'FETCH'

export const fetchAction = ({
  url, method, body, onSuccess, onError,
}) => ({
  type: FETCH,
  url,
  method,
  body,
  onSuccess,
  onError,
})

export const WitchcraftMiddleware = store => next => (action) => {
  if (action.type === FETCH) {
    const requestOptions = {
      url: action.url,
      method: action.method,
    }

    if (action.method === 'POST' || action.method === 'PUT' || action.method === 'PATCH') {
      requestOptions.body = JSON.stringify(action.body)
    }

    next(action)

    return request(requestOptions)
      .then(data => store.dispatch(action.onSuccess(data)))
      .catch(error => store.dispatch(action.onError(error)))
  }
  return next(action)
}
