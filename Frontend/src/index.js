import './index.css'

import React from 'react'
import ReactDOM from 'react-dom'
import { App } from './app/components/App'
import CssBaseline from '@material-ui/core/CssBaseline'
import { StoreProvider } from 'src/app/components/StoreProvider'
import { BrowserRouter } from 'react-router-dom'

ReactDOM.render(
  <StoreProvider>
    <BrowserRouter>
      <CssBaseline />
      <App />
    </BrowserRouter>
  </StoreProvider>
  , document.getElementById('react'),
)