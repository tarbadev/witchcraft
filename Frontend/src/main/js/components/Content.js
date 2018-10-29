import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom'

import Home from './Home'
import Recipes from './Recipes'
import Recipe from './Recipe'
import { NewRecipeContainer } from './NewRecipe'

export default class Content extends Component {
  render() {
    return (
      <div>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/recipes" component={Recipes} />
          <Route exact path="/recipes/new" component={NewRecipeContainer} />
          <Route exact path="/recipes/:id" component={Recipe} />
        </Switch>
      </div>
    )
  }
}
