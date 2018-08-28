import React, { Component } from 'react';
import {
  Route,
  HashRouter, Switch
} from "react-router-dom";

import Header from './Header'
import Content from './Content'

export default class App extends Component {
  render() {
    return (
      <div>
        <Header />
        <Content />
      </div>
    );
  }
}
