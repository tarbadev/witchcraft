import React from 'react'
import PropTypes from 'prop-types'
import { Route, Switch } from 'react-router-dom'

import { HomePageContainer } from './HomePage'
import { RecipesPageContainer } from 'src/recipes/pages/RecipesPage'
import { RecipePage } from 'src/recipes/pages/RecipePage'
import { NewRecipePageContainer } from 'src/recipes/pages/NewRecipePage'
import { EditRecipePageContainer } from 'src/recipes/pages/EditRecipePage'
import { CartsPageContainer } from 'src/carts/pages/CartsPage'
import { CartPageContainer } from 'src/carts/pages/CartPage'
import { NewCartPageContainer } from 'src/carts/pages/NewCartPage'
import { WeeksPage } from 'src/weeks/pages/WeeksPage'
import { WeekPage } from 'src/weeks/pages/WeekPage'

export const Content = () =>
  (
    <div>
      <Switch>
        <Route exact path="/" component={HomePageContainer} />
        <Route exact path="/recipes" component={RecipesPageContainer} />
        <Route exact path="/recipes/new" component={NewRecipePageContainer} />
        <Route exact path="/recipes/:id" component={RecipePage} />
        <Route exact path="/recipes/:id/edit" component={EditRecipePageContainer} />
        <Route exact path="/carts" component={CartsPageContainer} />
        <Route exact path="/carts/new" component={NewCartPageContainer} />
        <Route exact path="/carts/:id" component={CartPageContainer} />
        <Route exact path="/weeks" component={WeeksPage} />
        <Route exact path="/weeks/:year/:week" render={props => {
          const {
            match: {
              params: { year, week }
            }
          } = props

          return (<WeekPage
            key={`year=${year}&week=${week}`}
            year={year}
            weekNumber={week}
            {...props} />)
        }} />
      </Switch>
    </div>
  )

Content.propTypes = {
  match: PropTypes.object,
}