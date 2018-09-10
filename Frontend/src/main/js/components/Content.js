import React, { Component } from 'react';
import { Route } from 'react-router-dom'

import Home from './Home'
import Recipes from './Recipes'

export default class Content extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Home} />
        <Route path="/recipes" component={Recipes} />
      </div>
    )
  }
}
