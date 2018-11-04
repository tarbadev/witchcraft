import './index.css'

import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux'
import {store} from "./Store"
import { HashRouter } from "react-router-dom";

import App from 'app-components/App';

ReactDOM.render(
  <Provider store={store}>
    <HashRouter>
      <App />
    </HashRouter>
  </Provider>
  , document.getElementById('react')
);
