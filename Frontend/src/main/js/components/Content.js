import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom'

import Home from './Home'
import { RecipesContainer } from './Recipes'
import { RecipeContainer } from './Recipe'
import { NewRecipeContainer } from './NewRecipe'
import { EditRecipeContainer } from './EditRecipe'

export default class Content extends Component {
  render() {
    return (
      <div>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/recipes" component={RecipesContainer} />
          <Route exact path="/recipes/new" component={NewRecipeContainer} />
          <Route exact path="/recipes/:id" component={RecipeContainer} />
          <Route exact path="/recipes/:id/edit" component={EditRecipeContainer} />
        </Switch>
      </div>
    )
  }
}
