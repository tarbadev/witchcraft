import React from 'react'
import { Route, Switch } from 'react-router-dom'

import {Home} from './Home'
import { RecipesContainer } from './Recipes'
import { RecipeContainer } from './Recipe'
import { NewRecipeContainer } from './NewRecipe'
import { EditRecipeContainer } from './EditRecipe'
import { CartsPageContainer } from './CartsPage'
import { CartPageContainer } from './CartPage'
import { NewCartPageContainer } from './NewCartPage'
import { WeekPageContainer } from './WeekPage'

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
        <Route exact path="/carts/:id" component={CartPageContainer} />
        <Route exact path="/carts/new" component={NewCartPageContainer} />
        <Route exact path="/weeks/:year/:week" component={WeekPageContainer} />
      </Switch>
    </div>
  )
}
