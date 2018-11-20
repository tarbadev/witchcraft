import React from 'react'
import PropTypes from 'prop-types'
import {connect} from 'react-redux'
import {bindActionCreators} from 'redux'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import Button from '@material-ui/core/Button'
import IconButton from '@material-ui/core/IconButton'
import CircularProgress from '@material-ui/core/CircularProgress'
import FavoriteIcon from '@material-ui/icons/Favorite'
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Edit'
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import './RecipePage.css'
import { Step } from 'src/recipes/components/Step'
import { Ingredient } from 'src/recipes/components/Ingredient'

import { setFavorite, deleteRecipe } from 'src/recipes/actions/RecipeActions'

export const RecipePage = ({ recipe, history, toggleFavorite, isDeleting, deleteRecipe }) => {
  const onModifyButtonClick = () => {
    history.push(`/recipes/${recipe.id}/edit`)
  }

  let steps
  let ingredients
  let favoriteClassName = 'favoriteButton'

  if (recipe.steps) {
    steps = recipe.steps.map((step, index) => (<Grid item key={step.id} sm={12}>
      <Step number={index + 1} step={step.name}/>
    </Grid>))
  }

  if (recipe.ingredients) {
    ingredients = recipe.ingredients.map((ingredient) => (
      <Grid item key={ingredient.id} sm={12}>
        <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity}/>
      </Grid>
    ))
  }

  if (recipe.favorite) {
    favoriteClassName += ' ' + 'favorite'
  }

  return (
    <Grid container spacing={24}>
      <Grid item sm={12} name='title'>
        <Grid container justify='space-between'>
          <Grid item sm={8}>
            <Typography variant='headline' className="title">
              {recipe.name}
            </Typography>
            <IconButton onClick={() => toggleFavorite(recipe.id, !recipe.favorite)} className={favoriteClassName}>
              <FavoriteIcon/>
            </IconButton>
          </Grid>
          <Grid item className="circularProgressContainer">
            <Button className="modifyButton" variant='contained' onClick={onModifyButtonClick}>
              <EditIcon className="editIcon"/>
              Modify
            </Button>
            <Button className="deleteButton" variant='contained' onClick={() => deleteRecipe(recipe.id)} disabled={isDeleting}>
              <DeleteIcon className="deleteIcon"/>
              Delete
            </Button>
            {isDeleting && <CircularProgress size={24} className="circularProgress"/>}
          </Grid>
        </Grid>
      </Grid>
      <Grid item sm={12} name='top-bar' container justify='space-between'>
        <Grid item></Grid>
        <Grid item>
          <Button target='_blank' variant='contained' size='small' href={recipe.originUrl}>
            <OpenInNewIcon className="leftIcon"/>
            Go to recipe
          </Button>
        </Grid>
      </Grid>
      <Grid item sm={3} name='image'>
        <img src={recipe.imgUrl} className="image"/>
      </Grid>
      <Grid item sm={5} name='steps'>
        <Typography variant='title' gutterBottom>Steps</Typography>
        <Grid container alignItems='baseline' spacing={8}>
          {steps}
        </Grid>
      </Grid>
      <Grid item sm={4} name='ingredients'>
        <Typography variant='title' gutterBottom>Ingredients</Typography>
        <Grid container alignItems='baseline' spacing={8}>
          {ingredients}
        </Grid>
      </Grid>
    </Grid>
  )
}

RecipePage.propTypes = {
  recipe: PropTypes.object,
  history: PropTypes.object,
  toggleFavorite: PropTypes.func,
  isDeleting: PropTypes.bool,
  deleteRecipe: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    recipe: state.recipe,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    toggleFavorite: setFavorite,
    deleteRecipe: deleteRecipe
  },
  dispatch
  )
}

export const RecipePageContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(RecipePage)
