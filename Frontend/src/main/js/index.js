import './index.css'

import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter } from "react-router-dom";

import App from 'app-components/App';

ReactDOM.render(
  <HashRouter>
    <App />
  </HashRouter>
  , document.getElementById('react')
);
