import React from 'react'
import { Route, Switch } from 'react-router-dom'

import {Home} from './Home'
import { RecipesContainer } from './Recipes'
import { RecipeContainer } from './Recipe'
import { NewRecipeContainer } from './NewRecipe'
import { EditRecipeContainer } from './EditRecipe'
import { CartsPageContainer } from './CartsPage'
import { NewCartPageContainer } from './NewCartPage'

export const Content = () => {
  return (
    <div>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/recipes" component={RecipesContainer} />
        <Route exact path="/recipes/new" component={NewRecipeContainer} />
        <Route exact path="/recipes/:id" component={RecipeContainer} />
        <Route exact path="/recipes/:id/edit" component={EditRecipeContainer} />
        <Route exact path="/carts" component={CartsPageContainer} />
        <Route exact path="/carts/new" component={NewCartPageContainer} />
      </Switch>
    </div>
  )
}
