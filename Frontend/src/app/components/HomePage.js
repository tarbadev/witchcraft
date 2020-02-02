import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { RecipeList } from 'src/recipes/components/RecipeList'
import { MultipleDayPane } from 'src/weeks/components/MultipleDayPane'
import { PageTitle } from './PageTitle'
import { getFavoriteRecipes, getLatestRecipes } from '../../recipes/actions/RecipesActions'
import { getCurrentWeek, getWeek } from '../../weeks/actions/WeekActions'
import { useAppContext } from './StoreProvider'
import { DASHBOARD } from './Header'
import { initialState } from 'src/app/RootReducer'

export const HomePageContainer = () => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  useEffect(() => setHeaderConfig({ ...initialState.headerConfig, currentLink: DASHBOARD, title: DASHBOARD }), [])

  const [week, setWeek] = useState(state.week)
  const [latestRecipes, setLatestRecipes] = useState(state.pages.homePage.lastRecipes)
  const [favoriteRecipes, setFavoriteRecipes] = useState(state.pages.homePage.favoriteRecipes)

  useEffect(() => {
    const { year, week } = getCurrentWeek()
    dispatch(getFavoriteRecipes(data => setFavoriteRecipes(data)))
    dispatch(getLatestRecipes(data => setLatestRecipes(data)))
    dispatch(getWeek(year, week, data => setWeek(data)))
  }, [])

  return <HomePage
    favoriteRecipes={favoriteRecipes}
    latestRecipes={latestRecipes}
    week={week} />
}

export const HomePage = ({
  favoriteRecipes,
  latestRecipes,
  week,
}) => {

  return (
    <Grid container spacing={3}>
      <PageTitle />
      <Grid item xs={12}>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Current Week</Typography>
        <MultipleDayPane days={week.days} />
      </Grid>
      <Grid item xs={12}>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Favorite Recipes</Typography>
        <RecipeList recipes={favoriteRecipes} />
      </Grid>
      <Grid item xs={12}>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Last Added Recipes</Typography>
        <RecipeList recipes={latestRecipes} />
      </Grid>
    </Grid>
  )
}

HomePage.propTypes = {
  favoriteRecipes: PropTypes.array,
  latestRecipes: PropTypes.array,
  week: PropTypes.object,
}