import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import Button from '@material-ui/core/Button'
import IconButton from '@material-ui/core/IconButton'
import CircularProgress from '@material-ui/core/CircularProgress'
import TextField from '@material-ui/core/TextField'
import FavoriteIcon from '@material-ui/icons/Favorite'
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Edit'
import OpenInNewIcon from '@material-ui/icons/OpenInNew'

import './RecipePage.css'
import { Step } from 'src/recipes/components/Step'
import { Ingredient } from 'src/recipes/components/Ingredient'

import {
  deleteRecipe,
  hideEditableNotes,
  setFavorite,
  showEditableNotes,
  updateNotes,
} from 'src/recipes/actions/RecipeActions'
import { setState } from 'src/RootReducer'
import Paper from '@material-ui/core/Paper'
import { onRecipeImageNotFoundError } from 'src/App'
import CardMedia from '@material-ui/core/CardMedia'
import { PageTitle } from '../../PageTitle'

export const RecipePage = ({
  recipe,
  history,
  toggleFavorite,
  isDeleting,
  deleteRecipe,
  notes,
  editNotes,
  updateNotes,
  editableNotes,
  showEditableNotes,
  hideEditableNotes,
  notesInput,
}) => {
  const onModifyButtonClick = () => {
    history.push(`/recipes/${recipe.id}/edit`)
  }

  let steps
  let ingredients
  let favoriteClassName = 'favoriteButton'
  let notesComponent

  if (editableNotes) {
    notesComponent =
      <TextField
        autoFocus
        multiline={true}
        className='notes-container__editable-notes'
        value={notesInput}
        onChange={(event) => editNotes('recipePage.notesInput', event.target.value)}
        onBlur={hideEditableNotes} />
  } else if (notes) {
    notesComponent =
      <Typography
        variant='body2'
        className='notes-container__notes-content'
        onClick={showEditableNotes}>
        {notes}
      </Typography>
  } else {
    notesComponent =
      <Typography
        variant='body2'
        className='notes-container__empty-notes'
        onClick={showEditableNotes}>
        Add a note
      </Typography>
  }

  if (recipe.steps) {
    steps = recipe.steps.map((step, index) => (<Grid item key={step.id} xs={12}>
      <Step number={index + 1} step={step.name} />
    </Grid>))
  }

  if (recipe.ingredients) {
    ingredients = recipe.ingredients.map((ingredient) => (
      <Grid item key={ingredient.id} sm={12}>
        <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity} />
      </Grid>
    ))
  }

  if (recipe.favorite) {
    favoriteClassName += ' ' + 'favorite'
  }

  return (
    <Grid container spacing={3}>
      <PageTitle title={recipe.name} />
      <Grid item sm={12} name='title'>
        <Grid container justify='space-between'>
          <Grid item sm={8}>
            <Typography variant='h5' className="title witchcraft-title">
              {recipe.name}
            </Typography>
            <IconButton onClick={() => toggleFavorite(recipe.id, !recipe.favorite)} className={favoriteClassName}>
              <FavoriteIcon />
            </IconButton>
          </Grid>
          <Grid item className="circularProgressContainer">
            <Button className="modifyButton" variant='contained' onClick={onModifyButtonClick}>
              <EditIcon className="editIcon" />
              Modify
            </Button>
            <Button
              className="deleteButton"
              variant='contained'
              onClick={() => deleteRecipe(recipe.id)}
              disabled={isDeleting}>
              <DeleteIcon className="deleteIcon" />
              Delete
            </Button>
            {isDeleting && <CircularProgress size={24} className="circularProgress" />}
          </Grid>
        </Grid>
      </Grid>
      <Grid item sm={12} name='top-bar' container justify='space-between'>
        <Grid item />
        <Grid item>
          <Button target='_blank' variant='contained' size='small' href={recipe.originUrl}>
            <OpenInNewIcon className="leftIcon" />
            Go to recipe
          </Button>
        </Grid>
      </Grid>
      <Grid item container sm={3} name='image' direction="column" justify="flex-start" alignItems="stretch">
        <Grid item>
          <CardMedia image={recipe.imgUrl} component='img' className="image" onError={onRecipeImageNotFoundError} />
        </Grid>
        <Grid item container direction="column" justify="flex-start" alignItems="stretch">
          <Paper className='notes-container' square elevation={0}>
            <Grid container direction="column" justify="flex-start" alignItems="stretch">
              <Grid item>
                <Typography variant='h6' className='notes-container__notes-title'>
                  Notes
                </Typography>
              </Grid>
              <Grid item className='notes-container__notes'>
                {notesComponent}
              </Grid>
            </Grid>
          </Paper>
          <Grid item container direction="column" justify="flex-start" alignContent='center'>
            {editableNotes &&
            <Button
              className="notes-container__update-notes-button"
              color='primary'
              variant='outlined'
              onMouseDown={() => updateNotes(recipe.id, notesInput)}>
              Update Notes
            </Button>
            }
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={5} name='steps'>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Steps</Typography>
        <Grid container alignItems="baseline" spacing={1}>
          {steps}
        </Grid>
      </Grid>
      <Grid item xs={4} name='ingredients'>
        <Typography variant='h6' gutterBottom className='witchcraft-title'>Ingredients</Typography>
        <Grid container alignItems='baseline' spacing={1}>
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
  notes: PropTypes.string,
  editNotes: PropTypes.func,
  updateNotes: PropTypes.func,
  editableNotes: PropTypes.bool,
  showEditableNotes: PropTypes.func,
  hideEditableNotes: PropTypes.func,
  notesInput: PropTypes.string,
}

const mapStateToProps = state => {
  return {
    recipe: state.app.recipe,
    notes: state.app.recipePage.notes,
    editableNotes: state.app.recipePage.editableNotes,
    notesInput: state.app.recipePage.notesInput,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    {
      toggleFavorite: setFavorite,
      deleteRecipe: deleteRecipe,
      editNotes: setState,
      updateNotes: updateNotes,
      showEditableNotes: showEditableNotes,
      hideEditableNotes: hideEditableNotes,
    },
    dispatch,
  )
}

export const RecipePageContainer = connect(mapStateToProps, mapDispatchToProps)(RecipePage)
