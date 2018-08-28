import React, { Component } from 'react';
import { Route } from 'react-router-dom'

import Home from './Home'

export default class Content extends Component {
  render() {
    return (
        <Route path="/" component={Home} />
    )
  }
}
