import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'
import AddIcon from '@material-ui/icons/Add'

import { RecipeList } from 'src/recipes/components/RecipeList'

import { filterRecipes, getAllRecipes } from 'src/recipes/actions/RecipesActions'
import { makeStyles } from '@material-ui/core'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { RECIPE } from 'src/app/components/Header'
import { initialState } from 'src/app/RootReducer'
import MenuItem from '@material-ui/core/MenuItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'

const useStyles = makeStyles({
  paper: {
    padding: '.6em',
  },
})

export const RecipesPageContainer = ({ history }) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  const headerConfig = {
    ...initialState.headerConfig,
    currentLink: RECIPE,
    title: RECIPE,
    menuList: [
      <MenuItem
        key='menu-new-recipe'
        data-new-recipe
        onClick={() => history.push('/recipes/new')}
      >
        <ListItemIcon>
          <AddIcon />
        </ListItemIcon>
        <ListItemText primary="New Recipe" />
      </MenuItem>,
    ],
  }
  useEffect(() => setHeaderConfig(headerConfig), [])

  const [recipes, setRecipes] = useState(state.recipes)
  const [filteredRecipes, setFilteredRecipes] = useState(recipes)

  useEffect(
    () => dispatch(getAllRecipes(data => {
      setRecipes(data.recipes)
      setFilteredRecipes(data.recipes)
    })),
    [],
  )

  return <RecipesPage
    recipes={filteredRecipes}
    searchRecipe={(search) => setFilteredRecipes(filterRecipes(recipes, search))} />
}

RecipesPageContainer.propTypes = {
  history: PropTypes.object,
}

export const RecipesPage = ({ recipes, searchRecipe }) => {
  const classes = useStyles()

  return (
    <Grid container spacing={3}>
      <PageTitle title='Recipes' />
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
        <RecipeList recipes={recipes} />
      </Grid>
    </Grid>
  )
}

RecipesPage.propTypes = {
  recipes: PropTypes.array,
  searchRecipe: PropTypes.func,
}