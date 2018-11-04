import {createBrowserHistory} from "history"
import {applyMiddleware, createStore} from 'redux'
import {connectRouter, routerMiddleware} from 'connected-react-router'
import {composeWithDevTools} from 'redux-devtools-extension'
import thunk from 'redux-thunk'

import {reducer} from './RootReducer'
import { createHistoryObserver } from './HistoryObserver'
import { getAllRecipes } from 'app-actions/RecipesActions'
import { getRecipe } from 'app-actions/RecipeActions'
import { WitchcraftMiddleware } from 'app-root/WitchcraftMiddleware'

export const history = createBrowserHistory()
export const store = createStore(
	connectRouter(history)(reducer),
	composeWithDevTools(
		applyMiddleware(
			routerMiddleware(history),
			thunk,
			WitchcraftMiddleware
		)
	)
)

const pathRegexes = [
  { regex: /^#\/recipes\/(\d+)/, callback: (id) => store.dispatch(getRecipe(id)) },
  { regex: /^#\/recipes\/(\d+)\/edit/, callback: (id) => store.dispatch(getRecipe(id)) },
  { regex: /^#\/recipes$/, callback: () => store.dispatch(getAllRecipes()) },
]

const historyObserver = createHistoryObserver(pathRegexes)
historyObserver(history.location)
history.listen(historyObserver)
