import {createBrowserHistory} from "history"
import {applyMiddleware, createStore} from 'redux'
import {connectRouter, routerMiddleware} from 'connected-react-router'
import {composeWithDevTools} from 'redux-devtools-extension'
import thunk from 'redux-thunk'

import {reducer} from './RootReducer'

export const history = createBrowserHistory()
export const store = createStore(
	connectRouter(history)(reducer),
	composeWithDevTools(
		applyMiddleware(
			routerMiddleware(history),
			thunk
			//...middlewares
		)
	)
)
