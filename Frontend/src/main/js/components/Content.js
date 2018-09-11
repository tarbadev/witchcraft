import React, { Component } from 'react';
import { Route } from 'react-router-dom'

import Home from './Home'
import Recipes from './Recipes'
import Recipe from './Recipe'

export default class Content extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Home} />
        <Route exact path="/recipes" component={Recipes} />
        <Route path="/recipes/:id" component={Recipe} />
      </div>
    )
  }
}
