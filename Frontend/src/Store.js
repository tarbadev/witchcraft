import { createBrowserHistory } from 'history'
import { applyMiddleware, combineReducers, createStore } from 'redux'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import { composeWithDevTools } from 'redux-devtools-extension'
import thunk from 'redux-thunk'

import { reducer, setState } from './RootReducer'
import { createHistoryObserver } from './HistoryObserver'
import { WitchcraftMiddleware } from './WitchcraftMiddleware'

import { getAllRecipes, getFavoriteRecipes, getLatestRecipes } from 'src/recipes/actions/RecipesActions'
import { getRecipe, getRecipeNotes } from 'src/recipes/actions/RecipeActions'
import { getSupportedDomains } from 'src/recipes/actions/NewRecipeActions'
import { getAllCarts } from 'src/carts/actions/CartsActions'
import { getCart } from 'src/carts/actions/CartActions'
import { getWeek, getCurrentWeek } from 'src/weeks/actions/WeekActions'

export const history = createBrowserHistory()
const rootReducer = combineReducers({
  app: reducer,
  router: connectRouter(history),
})
export const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(routerMiddleware(history), thunk, WitchcraftMiddleware)))

const pathRegexes = [
  {
    regex: /^\/recipes\/(\d+)$/,
    callback: (id) => {
      store.dispatch(getRecipe(id))
      store.dispatch(getRecipeNotes(id))
    },
  }, {
    regex: /^\/recipes\/(\d+)\/edit$/,
    callback: (id) => store.dispatch(getRecipe(id)),
  }, {
    regex: /^\/recipes$/,
    callback: () => store.dispatch(getAllRecipes()),
  }, {
    regex: /^\/recipes\/new$/,
    callback: () => store.dispatch(getSupportedDomains()),
  }, {
    regex: /^\/carts$/,
    callback: () => store.dispatch(getAllCarts()),
  }, {
    regex: /^\/carts\/(\d+)$/,
    callback: (id) => store.dispatch(getCart(id)),
  }, {
    regex: /^\/carts\/new$/,
    callback: () => store.dispatch(getAllRecipes()),
  }, {
    regex: /^\/weeks\/(\d+)\/(\d+)$/,
    callback: ([year, week]) => {
      store.dispatch(getAllRecipes())
      store.dispatch(getWeek(year, week))
      store.dispatch(setState('weekPage.showSuccessMessage', false))
    },
  }, {
    regex: /^\/$/,
    callback: () => {
      const { year, week } = getCurrentWeek()
      store.dispatch(getWeek(year, week))
      store.dispatch(getFavoriteRecipes())
      store.dispatch(getLatestRecipes())
    },
  },
]

const historyObserver = createHistoryObserver(pathRegexes)
historyObserver(history.location)
history.listen(historyObserver)
