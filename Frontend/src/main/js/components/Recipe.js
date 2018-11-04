import React from 'react'
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import Button from '@material-ui/core/Button'
import IconButton from '@material-ui/core/IconButton'
import CircularProgress from '@material-ui/core/CircularProgress'
import FavoriteIcon from '@material-ui/icons/Favorite'
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Edit'
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import styles from 'app-components/Recipe.css'
import Step from 'app-components/Step'
import Ingredient from 'app-components/Ingredient'

import { setFavorite, deleteRecipe } from 'app-actions/RecipeActions'

export const Recipe = ({ recipe, history, toggleFavorite, isDeleting, deleteRecipe }) => {
  const onModifyButtonClick = () => {
    history.push(`/recipes/${recipe.id}/edit`)
  }

  let steps
  let ingredients
  let favoriteClassName = styles.favoriteButton

  if (recipe.steps) {
    steps = recipe.steps.map((step, index) => (<Grid item key={step.id} sm={12}>
      <Step number={index + 1} step={step.name}/>
    </Grid>))
  }

  if (recipe.ingredients) {
    ingredients = recipe.ingredients.map((ingredient, index) => (<Grid item key={ingredient.id} sm={12}>
      <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity}/>
    </Grid>))
  }

  if (recipe.favorite) {
    favoriteClassName += ' ' + styles.favorite
  }

  return (
    <Grid container spacing={24}>
      <Grid item sm={12} name="title">
        <Grid container justify="space-between">
          <Grid item sm={8}>
            <Typography variant="headline" className={styles.title}>
              {recipe.name}
            </Typography>
            <IconButton onClick={() => toggleFavorite(recipe.id, !recipe.favorite)} className={favoriteClassName}>
              <FavoriteIcon/>
            </IconButton>
          </Grid>
          <Grid item className={styles.circularProgressContainer}>
            <Button className={styles.modifyButton} variant="contained" onClick={onModifyButtonClick}>
              <EditIcon className={styles.editIcon}/>
              Modify
            </Button>
            <Button className={styles.deleteButton} variant="contained" onClick={() => deleteRecipe(recipe.id)} disabled={isDeleting}>
              <DeleteIcon className={styles.deleteIcon}/>
              Delete
            </Button>
            {isDeleting && <CircularProgress size={24} className={styles.circularProgress}/>}
          </Grid>
        </Grid>
      </Grid>
      <Grid item sm={12} name="top-bar" container justify="space-between">
        <Grid item></Grid>
        <Grid item>
          <Button target="_blank" variant="contained" size="small" href={recipe.originUrl}>
            <OpenInNewIcon className={styles.leftIcon}/>
            Go to recipe
          </Button>
        </Grid>
      </Grid>
      <Grid item sm={3} name="image">
        <img src={recipe.imgUrl} className={styles.image}/>
      </Grid>
      <Grid item sm={5} name="steps">
        <Typography variant="title" gutterBottom>Steps</Typography>
        <Grid container alignItems="baseline" spacing={8}>
          {steps}
        </Grid>
      </Grid>
      <Grid item sm={4} name="ingredients">
        <Typography variant="title" gutterBottom>Ingredients</Typography>
        <Grid container alignItems="baseline" spacing={8}>
          {ingredients}
        </Grid>
      </Grid>
    </Grid>
  )
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

export const RecipeContainer = connect(
	mapStateToProps,
	mapDispatchToProps
)(Recipe)
