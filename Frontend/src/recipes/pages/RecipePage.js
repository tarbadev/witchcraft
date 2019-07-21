import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
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

import { deleteRecipe, getRecipe, getRecipeNotes, setFavorite, updateNotes } from 'src/recipes/actions/RecipeActions'
import Paper from '@material-ui/core/Paper'
import { onRecipeImageNotFoundError } from 'src/App'
import CardMedia from '@material-ui/core/CardMedia'
import { PageTitle } from 'src/PageTitle'
import { useAppContext } from 'src/StoreProvider'
import { RECIPES } from 'src/Header'

export const RecipePageContainer = ({ match, history }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(RECIPES)

  const [recipe, setRecipe] = useState(state.recipe)
  const [notes, setNotes] = useState(state.pages.recipePage.notes)
  const [notesInput, setNotesInput] = useState(notes)
  const [isEditableNotes, setEditableNotes] = useState(state.pages.recipePage.editableNotes)
  const [isDeleting, setIsDeleting] = useState(state.pages.recipePage.isDeleting)

  const getRecipeSuccess = data => setNotes(data.notes)

  useEffect(() => dispatch(getRecipe(match.params.id, data => setRecipe(data))), [])
  useEffect(() => dispatch(getRecipeNotes(match.params.id, getRecipeSuccess)), [])

  const showEditableNotes = () => {
    setNotesInput(notes)
    setEditableNotes(true)
  }

  const deleteRecipeAndDisplayInProgress = () => {
    setIsDeleting(true)

    const deleteCallback = () => {
      setIsDeleting(false)
      history.push('/recipes')
    }
    dispatch(deleteRecipe(recipe.id, deleteCallback, deleteCallback))
  }

  return <RecipePage
    recipe={recipe}
    toggleFavorite={() => dispatch(setFavorite(recipe.id, !recipe.favorite, newRecipe => setRecipe(newRecipe)))}
    editRecipe={() => history.push(`/recipes/${recipe.id}/edit`)}
    deleteRecipe={deleteRecipeAndDisplayInProgress}
    notes={notes}
    editableNotes={isEditableNotes}
    showEditableNotes={showEditableNotes}
    hideEditableNotes={() => setEditableNotes(false)}
    updateNotes={() => dispatch(updateNotes(recipe.id, notesInput, getRecipeSuccess))}
    notesInput={notesInput}
    editNotes={newNotes => setNotesInput(newNotes)}
    isDeleting={isDeleting} />
}

RecipePageContainer.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
}

export const RecipePage = ({
  recipe,
  editRecipe,
  toggleFavorite,
  deleteRecipe,
  notes,
  isDeleting,
  editNotes,
  updateNotes,
  editableNotes,
  showEditableNotes,
  hideEditableNotes,
  notesInput,
}) => {
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
        onChange={(event) => editNotes(event.target.value)}
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
      <Grid item key={ingredient.id} xs={12}>
        <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity} />
      </Grid>
    ))
  }

  if (recipe.favorite) {
    favoriteClassName += ' ' + 'favorite'
  }

  return (
    <Grid container spacing={3} direction='row'>
      <PageTitle title={recipe.name} />
      <Grid item xs={12} name='title'>
        <Grid container justify='space-between'>
          <Grid item xs={8}>
            <Typography variant='h5' className='title witchcraft-title'>
              {recipe.name}
            </Typography>
            <IconButton
              href=''
              onClick={toggleFavorite}
              className={favoriteClassName}>
              <FavoriteIcon />
            </IconButton>
          </Grid>
          <Grid item className='circularProgressContainer'>
            <Button className='modifyButton' variant='contained' href='' onClick={editRecipe}>
              <EditIcon className='editIcon' />
            </Button>
            <Button
              className='deleteButton'
              variant='contained'
              onClick={deleteRecipe}
              href=''
              disabled={isDeleting}>
              <DeleteIcon className='deleteIcon' />
            </Button>
            {isDeleting && <CircularProgress size={24} className='circularProgress' />}
            <Button target='_blank' variant='contained' href={recipe.originUrl}>
              <OpenInNewIcon className='leftIcon' />
              Go to recipe
            </Button>
          </Grid>
        </Grid>
      </Grid>
      <Grid item container xs={3} direction='column' justify='flex-start' alignItems='stretch'>
        <Grid item>
          <CardMedia image={recipe.imgUrl} component='img' className='image' onError={onRecipeImageNotFoundError} />
        </Grid>
        <Grid item>
          <Paper className='notes-container' square elevation={0}>
            <Grid container direction='column' justify='flex-start' alignItems='stretch'>
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
          <Grid item container direction='column' justify='flex-start' alignContent='center'>
            {editableNotes &&
            <Button
              className='notes-container__update-notes-button'
              color='primary'
              href=''
              variant='outlined'
              onMouseDown={updateNotes}>
              Update Notes
            </Button>
            }
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={9} container spacing={3}>
        <Grid item xs={12} container justify='flex-end'>
          <Typography variant='h6' className='portions-title witchcraft-title'>
            Portions: <span className='portions-value'>{recipe.portions}</span>
          </Typography>
        </Grid>
        <Grid item xs={7} name='steps'>
          <Typography variant='h6' gutterBottom className='witchcraft-title'>Steps</Typography>
          <Grid container alignItems='baseline' spacing={1}>
            {steps}
          </Grid>
        </Grid>
        <Grid item xs={5} name='ingredients'>
          <Typography variant='h6' gutterBottom className='witchcraft-title'>Ingredients</Typography>
          <Grid container alignItems='baseline' spacing={1}>
            {ingredients}
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  )
}

RecipePage.propTypes = {
  recipe: PropTypes.object,
  editRecipe: PropTypes.func,
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