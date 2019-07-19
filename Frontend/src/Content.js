import React from 'react'
import { Route, Switch } from 'react-router-dom'

import { HomePage } from './HomePage'
import { RecipesPageContainer } from 'src/recipes/pages/RecipesPage'
import { RecipePageContainer } from 'src/recipes/pages/RecipePage'
import { NewRecipePageContainer } from 'src/recipes/pages/NewRecipePage'
import { EditRecipePageContainer } from 'src/recipes/pages/EditRecipePage'
import { CartsPageContainer } from 'src/carts/pages/CartsPage'
import { CartPageContainer } from 'src/carts/pages/CartPage'
import { NewCartPageContainer } from 'src/carts/pages/NewCartPage'
import { WeekPageContainer } from 'src/weeks/pages/WeekPage'
import { WeeksPage } from 'src/weeks/pages/WeeksPage'

export const Content = () => {
  return (
    <div>
      <Switch>
        <Route exact path="/" component={HomePage} />
        <Route exact path="/recipes" component={RecipesPageContainer} />
        <Route exact path="/recipes/new" component={NewRecipePageContainer} />
        <Route exact path="/recipes/:id" component={RecipePageContainer} />
        <Route exact path="/recipes/:id/edit" component={EditRecipePageContainer} />
        <Route exact path="/carts" component={CartsPageContainer} />
        <Route exact path="/carts/new" component={NewCartPageContainer} />
        <Route exact path="/carts/:id" component={CartPageContainer} />
        <Route exact path="/weeks" component={WeeksPage} />
        <Route exact path="/weeks/:year/:week" component={WeekPageContainer} />
      </Switch>
    </div>
  )
}
