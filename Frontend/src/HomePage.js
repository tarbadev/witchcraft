import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { RecipeList } from 'src/recipes/components/RecipeList'
import { WeekPane } from 'src/weeks/components/WeekPane'

export const HomePage = ({
  week,
  favoriteRecipes,
  latestRecipes,
}) => {
  return (
    <Grid container spacing={3}>
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
    week: state.week,
    favoriteRecipes: state.homePage.favoriteRecipes,
    latestRecipes: state.homePage.latestRecipes,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({}, dispatch)
}

export const HomePageContainer = connect(mapStateToProps, mapDispatchToProps)(HomePage)
