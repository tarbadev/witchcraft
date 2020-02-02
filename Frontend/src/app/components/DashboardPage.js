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
import { useMediaQuery, useTheme } from '@material-ui/core'
import { getCurrentDayIndex } from 'src/weeks/actions/WeekActions'

export const DashboardPageContainer = () => {
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('xs'))
  const isTablet = useMediaQuery(theme.breakpoints.down('sm'))

  const { state, dispatch, setHeaderConfig } = useAppContext()
  useEffect(() => setHeaderConfig({ ...initialState.headerConfig, currentLink: DASHBOARD, title: DASHBOARD }), [])

  const [week, setWeek] = useState(state.week)
  const [latestRecipes, setLatestRecipes] = useState(state.pages.homePage.lastRecipes)
  const [favoriteRecipes, setFavoriteRecipes] = useState(state.pages.homePage.favoriteRecipes)

  const currentWeek = getCurrentWeek()
  useEffect(() => {
    dispatch(getFavoriteRecipes(data => setFavoriteRecipes(data)))
    dispatch(getLatestRecipes(data => setLatestRecipes(data)))
    dispatch(getWeek(currentWeek.year, currentWeek.week, data => setWeek(data)))
  }, [])

  let days = week.days

  if (week.days.length > 0) {
    let dayIndex = getCurrentDayIndex()

    if (isMobile) {
      days = [week.days[dayIndex]]
    } else if (isTablet) {
      if (dayIndex > 4) {
        dayIndex = 4
      }
      days = [week.days[dayIndex], week.days[dayIndex + 1], week.days[dayIndex + 2]]
    }
  }

  return <DashboardPage
    favoriteRecipes={favoriteRecipes}
    latestRecipes={latestRecipes}
    days={days} />
}

export const DashboardPage = ({
  favoriteRecipes,
  latestRecipes,
  days,
}) => {

  return (
    <Grid container spacing={3}>
      <PageTitle />
      <Grid item xs={12}>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Current Week</Typography>
        <MultipleDayPane days={days} />
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

DashboardPage.propTypes = {
  favoriteRecipes: PropTypes.array,
  latestRecipes: PropTypes.array,
  days: PropTypes.array,
}