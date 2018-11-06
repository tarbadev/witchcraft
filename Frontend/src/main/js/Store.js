import {createBrowserHistory} from 'history'
import {applyMiddleware, createStore} from 'redux'
import {connectRouter, routerMiddleware} from 'connected-react-router'
import {composeWithDevTools} from 'redux-devtools-extension'
import thunk from 'redux-thunk'
import {weekNumber} from 'weeknumber'

import {reducer} from './RootReducer'
import {createHistoryObserver} from './HistoryObserver'
import {getAllRecipes} from 'app-actions/RecipesActions'
import {getAllCarts} from 'app-actions/CartsActions'
import {getRecipe} from 'app-actions/RecipeActions'
import {getCart} from 'app-actions/CartActions'
import {getWeek} from 'app-actions/WeekActions'
import {WitchcraftMiddleware} from 'app-root/WitchcraftMiddleware'

export const history = createBrowserHistory()
export const store = createStore(connectRouter(history)(reducer), composeWithDevTools(applyMiddleware(routerMiddleware(history), thunk, WitchcraftMiddleware)))

const getCurrentWeekUrl = () => {
  const week = weekNumber()
  const year = new Date().getYear() + 1900
  return `#/weeks/${year}/${week}`
}

const pathRegexes = [
  {
    regex: /^#\/recipes\/(\d+)$/,
    callback: (id) => store.dispatch(getRecipe(id))
  }, {
    regex: /^#\/recipes\/(\d+)\/edit$/,
    callback: (id) => store.dispatch(getRecipe(id))
  }, {
    regex: /^#\/recipes$/,
    callback: () => store.dispatch(getAllRecipes())
  }, {
    regex: /^#\/carts$/,
    callback: () => store.dispatch(getAllCarts())
  }, {
    regex: /^#\/carts\/(\d+)$/,
    callback: (id) => store.dispatch(getCart(id))
  }, {
    regex: /^#\/carts\/new$/,
    callback: () => store.dispatch(getAllRecipes())
  }, {
    regex: /^#\/weeks$/,
    callback: () => history.push(getCurrentWeekUrl())
  }, {
    regex: /^#\/weeks\/(\d+)\/(\d+)$/,
    callback: ([year, week]) => store.dispatch(getWeek(year, week))
  }
]

const historyObserver = createHistoryObserver(pathRegexes)
historyObserver(history.location)
history.listen(historyObserver)
