import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
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
import { IngredientContainer } from 'src/recipes/components/Ingredient'

import { deleteRecipe, getRecipe, getRecipeNotes, setFavorite, updateNotes } from 'src/recipes/actions/RecipeActions'
import Paper from '@material-ui/core/Paper'
import { onRecipeImageNotFoundError } from 'src/app/components/App'
import CardMedia from '@material-ui/core/CardMedia'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { RECIPES } from 'src/app/components/Header'
import { OneLineEditableFieldContainer } from 'src/app/components/OneLineEditableField'
import { saveStepNote } from '../actions/StepActions'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import { ConverterContainer } from '../components/Converter'
import DialogTitle from '@material-ui/core/DialogTitle'
import { PortionsContainer } from '../components/Portions'
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp'
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown'

export const RecipePageContainer = ({ match, history }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(RECIPES)

  const [recipe, setRecipe] = useState(state.recipe)
  const [notes, setNotes] = useState(state.pages.recipePage.notes)
  const [isDeleting, setIsDeleting] = useState(state.pages.recipePage.isDeleting)
  const [isConfirmDeleteDialogOpen, setIsConfirmDeleteDialogOpen] = useState(false)
  const [isConverterOpen, setConverterOpen] = useState(false)

  const getRecipeNoteSuccess = data => setNotes(data.notes)
  const loadRecipe = () => dispatch(getRecipe(match.params.id, data => setRecipe(data)))

  useEffect(loadRecipe, [match.params.id])
  useEffect(() => dispatch(getRecipeNotes(match.params.id, getRecipeNoteSuccess)), [match.params.id])

  const deleteRecipeAndDisplayInProgress = () => {
    setIsDeleting(true)

    const deleteCallback = () => {
      setIsDeleting(false)
      history.push('/recipes')
    }
    dispatch(deleteRecipe(recipe.id, deleteCallback, deleteCallback))
  }

  const updateRecipeWithUpdatedStep = newStep => {
    const index = recipe.steps.findIndex(step => step.id === newStep.id)
    const newRecipe = { ...recipe }

    newRecipe.steps[index] = newStep

    setRecipe(newRecipe)
  }

  const onPortionsUp = () => {
    const newPortions = Number(recipe.portions) + 1
    const newIngredients = getUpdatedIngredientPortions(newPortions / recipe.portions)
    setRecipe({ ...recipe, portions: newPortions, ingredients: newIngredients })
  }

  const onPortionsDown = () => {
    const newPortions = Number(recipe.portions) - 1
    const newIngredients = getUpdatedIngredientPortions(newPortions / recipe.portions)
    setRecipe({ ...recipe, portions: newPortions, ingredients: newIngredients })
  }

  const getUpdatedIngredientPortions = difference => recipe.ingredients.map(ingredient => ({
    ...ingredient,
    quantity: ingredient.quantity * difference,
  }))

  return <RecipePage
    recipe={recipe}
    toggleFavorite={() => dispatch(setFavorite(recipe.id, !recipe.favorite, newRecipe => setRecipe(newRecipe)))}
    editRecipe={() => history.push(`/recipes/${recipe.id}/edit`)}
    deleteRecipe={() => setIsConfirmDeleteDialogOpen(true)}
    closeConfirmDeleteDialogOpen={() => setIsConfirmDeleteDialogOpen(false)}
    confirmDeleteRecipe={deleteRecipeAndDisplayInProgress}
    isConfirmDeleteDialogOpen={isConfirmDeleteDialogOpen}
    notes={notes}
    updateNotes={newNotes => dispatch(updateNotes(recipe.id, newNotes, getRecipeNoteSuccess))}
    isDeleting={isDeleting}
    onStepNoteSaveButtonClick={(stepId, newNote) => dispatch(saveStepNote(recipe.id,
      stepId,
      newNote,
      updateRecipeWithUpdatedStep))}
    onIngredientDeletion={loadRecipe}
    isConverterOpen={isConverterOpen}
    displayConverter={() => setConverterOpen(true)}
    closeConverter={() => setConverterOpen(false)}
    onPortionsUpdated={recipe => setRecipe(recipe)}
    portionsUp={onPortionsUp}
    portionsDown={onPortionsDown}
  />
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
  confirmDeleteRecipe,
  closeConfirmDeleteDialogOpen,
  isConfirmDeleteDialogOpen,
  notes,
  isDeleting,
  updateNotes,
  onStepNoteSaveButtonClick,
  onIngredientDeletion,
  displayConverter,
  closeConverter,
  isConverterOpen,
  onPortionsUpdated,
  portionsUp,
  portionsDown,
}) => {
  let steps
  let ingredients

  let favoriteClassName = 'favoriteButton'

  if (recipe.steps) {
    steps = recipe.steps
      .sort((a, b) => a.id > b.id ? 1 : -1)
      .map((step, index) => (
        <Grid item key={step.id} xs={12}>
          <Step
            number={index + 1}
            step={step.name}
            note={step.note}
            onSaveNote={newNote => onStepNoteSaveButtonClick(step.id, newNote)} />
        </Grid>))
  }

  if (recipe.ingredients) {
    ingredients = recipe.ingredients.map((ingredient, index) => (
      <Grid item key={ingredient.id} xs={12}>
        <IngredientContainer
          index={index}
          ingredient={ingredient}
          recipeId={recipe.id}
          deleteCallback={onIngredientDeletion} />
      </Grid>
    ))
  }

  if (recipe.favorite) {
    favoriteClassName += ' ' + 'favorite'
  }

  const capitalizedRecipeName = recipe.name.charAt(0).toUpperCase() + recipe.name.slice(1)

  return <Grid container spacing={3}>
    <PageTitle title={recipe.name} />
    <Grid item xs={12} name='title' container justify='space-between'>
      <Grid item xs>
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
      <Grid item xs={4}>
        <Button
          variant='contained'
          href=''
          style={{ marginRight: 10 }}
          data-open-converter
          onClick={displayConverter}
        >
          Converter
        </Button>
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
        <Dialog
          open={isConfirmDeleteDialogOpen}
          onClose={closeConfirmDeleteDialogOpen}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle data-confirm-delete-title>
            {`Delete recipe ${capitalizedRecipeName}?`}
          </DialogTitle>
          <DialogActions>
            <Button onClick={closeConfirmDeleteDialogOpen} autoFocus>
              Cancel
            </Button>
            <Button onClick={confirmDeleteRecipe} data-confirm-delete-button color='secondary'>
              Delete
            </Button>
          </DialogActions>
        </Dialog>
        {isDeleting && <CircularProgress size={24} className='circularProgress' />}
        <Button target='_blank' variant='contained' href={recipe.originUrl}>
          <OpenInNewIcon className='leftIcon' />
          Go to recipe
        </Button>
      </Grid>
    </Grid>
    <Grid item container xs={3} direction='column' justify='flex-start' alignItems='stretch'>
      <Grid item container justify='center'>
        {recipe.imgUrl
          ? <CardMedia image={recipe.imgUrl} component='img' className='image' onError={onRecipeImageNotFoundError} />
          : <CircularProgress />
        }
      </Grid>
      <Grid item>
        <Paper className='notes-container notes-container-color' square elevation={0}>
          <Grid container direction='column' justify='flex-start' alignItems='stretch'>
            <Grid item>
              <Typography variant='h6' className='notes-container__notes-title'>
                Notes
              </Typography>
            </Grid>
            <Grid item className='notes-container__notes'>
              <OneLineEditableFieldContainer initialValue={notes} onSaveClick={updateNotes} />
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Grid>
    <Grid item xs={9} container spacing={3} justify='flex-start' alignItems='flex-start'>
      <Grid item xs={12} container justify='flex-end' direction='row' alignItems='center' spacing={1}>
        <Grid item>
          <PortionsContainer
            recipeId={recipe.id}
            portions={`${recipe.portions}`}
            onPortionsUpdated={onPortionsUpdated}
          />
        </Grid>
        <Grid item>
          <Grid container direction='column' spacing={1}>
            <Grid item>
              <Button
                aria-label="Portions up"
                data-portions-button-up
                onClick={portionsUp}
                color='primary'
                style={{ padding: 0, fontSize: '1.2rem' }}
              >
                <KeyboardArrowUpIcon style={{ fontSize: '1.2rem' }} />
              </Button>
            </Grid>
            <Grid item>
              <Button
                aria-label="Portions down"
                data-portions-button-down
                onClick={portionsDown}
                color='primary'
                style={{ padding: 0, fontSize: '1.2rem' }}
              >
                <KeyboardArrowDownIcon style={{ fontSize: '1.2rem' }} />
              </Button>
            </Grid>
          </Grid>
        </Grid>
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
    <ConverterContainer open={isConverterOpen} onClose={closeConverter} />
  </Grid>
}

RecipePage.propTypes = {
  recipe: PropTypes.object,
  editRecipe: PropTypes.func,
  toggleFavorite: PropTypes.func,
  isDeleting: PropTypes.bool,
  deleteRecipe: PropTypes.func,
  confirmDeleteRecipe: PropTypes.func,
  closeConfirmDeleteDialogOpen: PropTypes.func,
  isConfirmDeleteDialogOpen: PropTypes.bool,
  notes: PropTypes.string,
  updateNotes: PropTypes.func,
  onStepNoteSaveButtonClick: PropTypes.func,
  onIngredientDeletion: PropTypes.func,
  displayConverter: PropTypes.func,
  closeConverter: PropTypes.func,
  isConverterOpen: PropTypes.bool,
  onPortionsUpdated: PropTypes.func,
  portionsUp: PropTypes.func,
  portionsDown: PropTypes.func,
}