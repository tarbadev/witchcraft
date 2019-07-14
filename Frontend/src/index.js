import './index.css'

import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { history, store } from './Store'
import { ConnectedRouter } from 'connected-react-router'

import { App } from './App'
import { StoreProvider } from './StoreProvider'

ReactDOM.render(
  <StoreProvider>
    <Provider store={store}>
      <ConnectedRouter history={history}>
        <App />
      </ConnectedRouter>
    </Provider>
  </StoreProvider>
  , document.getElementById('react'),
)
