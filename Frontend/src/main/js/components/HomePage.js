import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { RecipeList } from './RecipeList'
import { WeekPane } from './WeekPane'

export const HomePage = ({
  week,
  favoriteRecipes,
}) => {
  return (
    <Grid container spacing={24}>
      <Grid item xs={12}>
        <Typography variant='title' gutterBottom>Current Week</Typography>
        <WeekPane week={week} />
      </Grid>
      <Grid item xs={12}>
        <Typography variant='title' gutterBottom>Favorite Recipes</Typography>
        <RecipeList recipes={favoriteRecipes} />
      </Grid>
      <Grid item xs={12}>
        <Typography variant='title' gutterBottom>Last Added Recipes</Typography>
      </Grid>
    </Grid>
  )
}

HomePage.propTypes = {
  week: PropTypes.object,
  favoriteRecipes: PropTypes.array,
}

const mapStateToProps = state => {
  return {
    week: state.week,
    favoriteRecipes: state.homePage.favoriteRecipes,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
  }, dispatch)
}

export const HomePageContainer = connect(mapStateToProps, mapDispatchToProps)(HomePage)
