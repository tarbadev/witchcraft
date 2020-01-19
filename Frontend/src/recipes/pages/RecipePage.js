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
import { withStyles } from '@material-ui/core/styles'
import DialogContent from '@material-ui/core/DialogContent'
import MuiDialogTitle from '@material-ui/core/DialogTitle'
import CloseIcon from '@material-ui/icons/Close'
import Draggable from 'react-draggable'
import { ResizableBox } from 'react-resizable'
import { makeStyles } from '@material-ui/core/styles'

const PaperComponent = props => (
  <Draggable cancel={'[class*="MuiDialogContent-root"]'}>
    <Paper {...props} />
  </Draggable>
)

const styles = theme => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
    cursor: 'move',
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
})

const converterStyles = makeStyles({
  resizable: {
    position: 'relative',
    '& .react-resizable-handle': {
      position: 'absolute',
      width: 20,
      height: 20,
      bottom: 0,
      right: 0,
      background:
        'url(\'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2IDYiIHN0eWxlPSJiYWNrZ3JvdW5kLWNvbG9yOiNmZmZmZmYwMCIgeD0iMHB4IiB5PSIwcHgiIHdpZHRoPSI2cHgiIGhlaWdodD0iNnB4Ij48ZyBvcGFjaXR5PSIwLjMwMiI+PHBhdGggZD0iTSA2IDYgTCAwIDYgTCAwIDQuMiBMIDQgNC4yIEwgNC4yIDQuMiBMIDQuMiAwIEwgNiAwIEwgNiA2IEwgNiA2IFoiIGZpbGw9IiMwMDAwMDAiLz48L2c+PC9zdmc+\')',
      'background-position': 'bottom right',
      padding: '0 3px 3px 0',
      'background-repeat': 'no-repeat',
      'background-origin': 'content-box',
      'box-sizing': 'border-box',
      cursor: 'se-resize',
    },
  },
})

const DialogTitle = withStyles(styles)(({ children, classes, onClose, ...other }) => {
  return (
    <MuiDialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton aria-label="close" className={classes.closeButton} onClick={onClose} data-close-converter>
          <CloseIcon />
        </IconButton>
      ) : null}
    </MuiDialogTitle>
  )
})

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

  useEffect(loadRecipe, [])
  useEffect(() => dispatch(getRecipeNotes(match.params.id, getRecipeNoteSuccess)), [])

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
        <Button variant='contained' href='' data-open-converter onClick={displayConverter}>Converter</Button>
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
    <Dialog
      aria-labelledby="customized-dialog-title"
      open={isConverterOpen}
      PaperComponent={PaperComponent}
      hideBackdrop
      disableBackdropClick
      disableEscapeKeyDown
      data-converter-container>
      <ResizableBox className={converterStyles().resizable}>
        <DialogTitle id="customized-dialog-title" onClose={closeConverter}>
          Converter
        </DialogTitle>
        <DialogContent dividers>
          <Typography gutterBottom>
            Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis
            in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
          </Typography>
          <Typography gutterBottom>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis
            lacus vel augue laoreet rutrum faucibus dolor auctor.
          </Typography>
          <Typography gutterBottom>
            Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel
            scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus
            auctor fringilla.
          </Typography>
        </DialogContent>
        {/*<DialogActions>*/}
        {/*  <Button autoFocus onClick={handleClose} color="primary">*/}
        {/*    Save changes*/}
        {/*  </Button>*/}
        {/*</DialogActions>*/}
      </ResizableBox>
    </Dialog>
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
}