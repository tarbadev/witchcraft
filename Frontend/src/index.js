import './index.css'

import React from 'react'
import ReactDOM from 'react-dom'
import { Router } from 'react-router'

import { App } from './App'
import { StoreProvider } from './StoreProvider'
import { createBrowserHistory } from 'history'

ReactDOM.render(
  <StoreProvider>
    <Router history={createBrowserHistory()}>
      <App />
    </Router>
  </StoreProvider>
  , document.getElementById('react'),
)
