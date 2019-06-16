import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'

import { RecipeList } from 'src/recipes/components/RecipeList'

import { filterRecipes } from 'src/recipes/actions/RecipesActions'
import { makeStyles } from '@material-ui/core'

const useStyles = makeStyles({
  paper: {
    padding: '.6em',
  },
})

export const RecipesPage = ({ recipes, searchRecipe, history }) => {
  const classes = useStyles()
  const onNewRecipeClick = () => {
    history.push('/recipes/new')
  }

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper elevation={1} className={classes.paper}>
          <TextField
            fullWidth
            label='Search for a recipe'
            type='search'
            className='recipes__search-input'
            onChange={(e) => searchRecipe(e.target.value)} />
        </Paper>
      </Grid>
      <Grid item xs={12}>
        <Button variant='contained' color='primary' onClick={onNewRecipeClick}>
          New Recipe
        </Button>
      </Grid>
      <Grid item xs={12}>
        <RecipeList recipes={recipes} />
      </Grid>
    </Grid>
  )
}

RecipesPage.propTypes = {
  recipes: PropTypes.array,
  searchRecipe: PropTypes.func,
  classes: PropTypes.object,
  history: PropTypes.object,
}

const mapStateToProps = state => {
  return {
    recipes: state.app.recipes,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    searchRecipe: filterRecipes,
  }, dispatch)
}

export const RecipesPageContainer = connect(mapStateToProps, mapDispatchToProps)(RecipesPage)
