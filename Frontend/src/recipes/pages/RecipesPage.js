import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'
import { withStyles } from '@material-ui/core/styles'

import { RecipeList } from 'src/recipes/components/RecipeList'

import { filterRecipes } from 'src/recipes/actions/RecipesActions'

const styles = () => ({
  paper: {
    padding: '.6em'
  }
})

export const RecipesPage = ({recipes, searchRecipe, classes = {}, history}) => {
  const onNewRecipeClick = () => {
    history.push('/recipes/new')
  }

  return (
    <Grid container spacing={24}>
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
      <Grid item>
        <RecipeList recipes={recipes}/>
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
    recipes: state.recipes
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    searchRecipe: filterRecipes
  }, dispatch)
}

const RecipesPageWithStyles = withStyles(styles)(RecipesPage)

export const RecipesPageContainer = connect(mapStateToProps, mapDispatchToProps)(RecipesPageWithStyles)