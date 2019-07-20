import { createBrowserHistory } from 'history'
import { applyMiddleware, combineReducers, createStore } from 'redux'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import { composeWithDevTools } from 'redux-devtools-extension'
import thunk from 'redux-thunk'

import { reducer, setState } from './RootReducer'
import { createHistoryObserver } from './HistoryObserver'
import { WitchcraftMiddleware } from './WitchcraftMiddleware'

export const history = createBrowserHistory()
const rootReducer = combineReducers({
  app: reducer,
  router: connectRouter(history),
})
export const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(
  routerMiddleware(history),
  thunk,
  WitchcraftMiddleware,
)))

const pathRegexes = [
  {
    regex: /^\/recipes\/(\d+)$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Recipes'))
    },
  }, {
    regex: /^\/recipes\/(\d+)\/edit$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Recipes'))
    },
  }, {
    regex: /^\/recipes$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Recipes'))
    },
  }, {
    regex: /^\/recipes\/new$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Recipes'))
    },
  }, {
    regex: /^\/carts$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Carts'))
    },
  }, {
    regex: /^\/carts\/(\d+)$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Carts'))
    },
  }, {
    regex: /^\/carts\/new$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Carts'))
    },
  }, {
    regex: /^\/weeks\/(\d+)\/(\d+)$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Weeks'))
    },
  }, {
    regex: /^\/$/,
    callback: () => {
      store.dispatch(setState('currentPage', 'Home'))
    },
  },
]

const historyObserver = createHistoryObserver(pathRegexes)
historyObserver(history.location)
history.listen(historyObserver)
