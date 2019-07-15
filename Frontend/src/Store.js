import { createBrowserHistory } from 'history'
import { applyMiddleware, combineReducers, createStore } from 'redux'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import { composeWithDevTools } from 'redux-devtools-extension'
import thunk from 'redux-thunk'

import { reducer, setState } from './RootReducer'
import { createHistoryObserver } from './HistoryObserver'
import { WitchcraftMiddleware } from './WitchcraftMiddleware'

import { getAllRecipes } from 'src/recipes/actions/RecipesActions'
import { getRecipe, getRecipeNotes } from 'src/recipes/actions/RecipeActions'
import { getWeek } from 'src/weeks/actions/WeekActions'

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
    callback: (id) => {
      store.dispatch(getRecipe(id, data => store.dispatch(setState('recipe', data))))
      store.dispatch(getRecipeNotes(id))
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
      store.dispatch(getAllRecipes())
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
      store.dispatch(getAllRecipes())
      store.dispatch(setState('currentPage', 'Carts'))
    },
  }, {
    regex: /^\/weeks\/(\d+)\/(\d+)$/,
    callback: ([year, week]) => {
      store.dispatch(getAllRecipes())
      store.dispatch(getWeek(year, week, data => store.dispatch(setState('week', data))))
      store.dispatch(setState('pages.weekPage.showSuccessMessage', false))
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
