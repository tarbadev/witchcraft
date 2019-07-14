import { request } from './Utils'
import match from 'conditional-expression'

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
      .catch(error => action.onError && store.dispatch(action.onError(error)))
  }
  return next(action)
}

export const applyMiddleware = dispatch => action =>
  dispatch(action) ||
  match(action.type)
    .equals(FETCH).then(() => {
      const requestOptions = {
        url: action.url,
        method: action.method,
      }

      if (action.method === 'POST' || action.method === 'PUT' || action.method === 'PATCH') {
        requestOptions.body = JSON.stringify(action.body)
      }

      request(requestOptions)
        .then(data => action.onSuccess(data))
        .catch(error => action.onError && action.onError(error))
    })
    .equals('@@router/LOCATION_CHANGE').then(() => {
      //console.log('push')
    })
    .else(null)