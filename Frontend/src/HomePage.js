import React, { useEffect, useState } from 'react'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { RecipeList } from 'src/recipes/components/RecipeList'
import { WeekPane } from 'src/weeks/components/WeekPane'
import { PageTitle } from './PageTitle'
import { getFavoriteRecipes, getLatestRecipes } from './recipes/actions/RecipesActions'
import { getCurrentWeek, getWeek } from './weeks/actions/WeekActions'
import { useAppContext } from './StoreProvider'

const useFavoriteRecipes = () => {
  const { state, dispatch } = useAppContext()
  const [favoriteRecipes, setFavoriteRecipes] = useState(state.pages.homePage.favoriteRecipes)

  useEffect(() => {
    dispatch(getFavoriteRecipes(data => setFavoriteRecipes(data)))
  }, [])

  return favoriteRecipes
}

const useLatestRecipes = () => {
  const { state, dispatch } = useAppContext()
  const [latestRecipes, setLatestRecipes] = useState(state.pages.homePage.lastRecipes)

  useEffect(() => {
    dispatch(getLatestRecipes(data => setLatestRecipes(data)))
  }, [])

  return latestRecipes
}

const useWeek = () => {
  const { state, dispatch } = useAppContext()
  const [week, setWeek] = useState(state.week)

  useEffect(() => {
    const { year, week } = getCurrentWeek()
    dispatch(getWeek(year, week, data => setWeek(data)))
  }, [])

  return week
}

export const HomePage = () => {
  const favoriteRecipes = useFavoriteRecipes()
  const latestRecipes = useLatestRecipes()
  const week = useWeek()

  return (
    <Grid container spacing={3}>
      <PageTitle />
      <Grid item xs={12}>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Current Week</Typography>
        <WeekPane week={week} />
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