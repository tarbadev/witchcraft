import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { RecipeList } from 'src/recipes/components/RecipeList'
import { WeekPane } from 'src/weeks/components/WeekPane'
import { PageTitle } from './PageTitle'

export const HomePage = ({
  week,
  favoriteRecipes,
  latestRecipes,
}) => {
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

HomePage.propTypes = {
  week: PropTypes.object,
  favoriteRecipes: PropTypes.array,
  latestRecipes: PropTypes.array,
}

const mapStateToProps = state => {
  return {
    week: state.app.week,
    favoriteRecipes: state.app.pages.homepage.favoriteRecipes,
    latestRecipes: state.app.pages.homepage.latestRecipes,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({}, dispatch)
}

export const HomePageContainer = connect(mapStateToProps, mapDispatchToProps)(HomePage)
