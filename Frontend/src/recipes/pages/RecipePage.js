import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { makeStyles, useMediaQuery, useTheme } from '@material-ui/core'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'
import ExpansionPanel from '@material-ui/core/ExpansionPanel'
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary'
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails'
import CardMedia from '@material-ui/core/CardMedia'
import List from '@material-ui/core/List'
import IconButton from '@material-ui/core/IconButton'
import Button from '@material-ui/core/Button'
import MenuItem from '@material-ui/core/MenuItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogActions from '@material-ui/core/DialogActions'
import Dialog from '@material-ui/core/Dialog'
import Divider from '@material-ui/core/Divider'
import Paper from '@material-ui/core/Paper'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import FavoriteIcon from '@material-ui/icons/Favorite'
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder'
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Edit'
import OpenInNewIcon from '@material-ui/icons/OpenInNew'
import TransformIcon from '@material-ui/icons/Transform'
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp'
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown'
import { useAppContext } from 'src/app/components/StoreProvider'
import { RECIPE } from 'src/app/components/Header'
import { deleteRecipe, getRecipe, getRecipeNotes, setFavorite, updateNotes } from 'src/recipes/actions/RecipeActions'
import { PageTitle } from 'src/app/components/PageTitle'
import { Step } from 'src/recipes/components/Step'
import { saveStepNote } from 'src/recipes/actions/StepActions'
import { IngredientContainer } from 'src/recipes/components/IngredientContainer'
import { onRecipeImageNotFoundError } from 'src/app/components/App'
import { ConverterContainer } from 'src/recipes/components/Converter'
import { OneLineEditableFieldContainer } from 'src/app/components/OneLineEditableField'
import { PortionsContainer } from 'src/recipes/components/Portions'

import './RecipePage.css'

export const RecipePage = ({ match, history }) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  const theme = useTheme()
  const isSmallAndUp = useMediaQuery(theme.breakpoints.up('sm'))
  const [isConfirmDeleteDialogOpen, setIsConfirmDeleteDialogOpen] = useState(false)
  const [recipe, setRecipe] = useState(state.recipe)
  const [isConverterOpen, setConverterOpen] = useState(false)
  const [notes, setNotes] = useState('')
  const [panelIngredientsExpanded, setPanelIngredientsExpanded] = useState(isSmallAndUp)
  const [panelStepsExpanded, setPanelStepsExpanded] = useState(isSmallAndUp)

  const loadRecipe = () => dispatch(getRecipe(match.params.id, data => setRecipe(data)))
  const updateRecipeWithUpdatedStep = newStep => {
    const index = recipe.steps.findIndex(step => step.id === newStep.id)
    const newRecipe = { ...recipe }

    newRecipe.steps[index] = newStep

    setRecipe(newRecipe)
  }
  const getRecipeNoteSuccess = data => setNotes(data.notes)

  useEffect(loadRecipe, [match.params.id])
  useEffect(() => {
    setHeaderConfig({
      currentLink: RECIPE,
      menuList: [
        <MenuItem key='menu-edit' data-edit-button onClick={() => history.push(`/recipes/${match.params.id}/edit`)}>
          <ListItemIcon>
            <EditIcon />
          </ListItemIcon>
          <ListItemText primary="Edit" />
        </MenuItem>,
        <MenuItem key='menu-delete' data-delete-button onClick={() => setIsConfirmDeleteDialogOpen(true)}>
          <ListItemIcon>
            <DeleteIcon />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>,
        <MenuItem key='menu-original-recipe' component='a' target='_blank' href={recipe.originUrl}>
          <ListItemIcon>
            <OpenInNewIcon />
          </ListItemIcon>
          <ListItemText primary="Original recipe" />
        </MenuItem>,
        <Divider key='menu-divider' />,
        <MenuItem key='menu-converter' data-open-converter onClick={() => setConverterOpen(true)}>
          <ListItemIcon>
            <TransformIcon />
          </ListItemIcon>
          <ListItemText primary="Converter" />
        </MenuItem>,
      ],
    })
  }, [recipe])
  useEffect(() => dispatch(getRecipeNotes(match.params.id, getRecipeNoteSuccess)), [match.params.id])
  useEffect(() => setPanelIngredientsExpanded(isSmallAndUp), [isSmallAndUp])
  useEffect(() => setPanelStepsExpanded(isSmallAndUp), [isSmallAndUp])

  const deleteCallback = () => history.push('/recipes')

  const getUpdatedIngredientPortions = difference => recipe.ingredients.map(ingredient => ({
    ...ingredient,
    quantity: Number((ingredient.quantity * difference).toFixed(3)),
  }))

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

  return <RecipePageDisplay
    recipe={recipe}
    onIngredientDeletion={loadRecipe}
    onStepNoteSaveButtonClick={(stepId, newNote) => dispatch(saveStepNote(
      recipe.id,
      stepId,
      newNote,
      updateRecipeWithUpdatedStep,
    ))}
    toggleFavorite={() => dispatch(setFavorite(recipe.id, !recipe.favorite, newRecipe => setRecipe(newRecipe)))}
    closeConfirmDeleteDialogOpen={() => setIsConfirmDeleteDialogOpen(false)}
    confirmDeleteRecipe={() => dispatch(deleteRecipe(recipe.id, deleteCallback, deleteCallback))}
    isConfirmDeleteDialogOpen={isConfirmDeleteDialogOpen}
    isConverterOpen={isConverterOpen}
    closeConverter={() => setConverterOpen(false)}
    notes={notes}
    updateNotes={newNotes => dispatch(updateNotes(recipe.id, newNotes, getRecipeNoteSuccess))}
    onPortionsUpdated={recipe => setRecipe(recipe)}
    portionsUp={onPortionsUp}
    portionsDown={onPortionsDown}
    panelIngredientsExpanded={panelIngredientsExpanded}
    panelStepsExpanded={panelStepsExpanded}
    toggleIngredientsPanel={() => setPanelIngredientsExpanded(!panelIngredientsExpanded)}
    toggleStepsPanel={() => setPanelStepsExpanded(!panelStepsExpanded)}
    displayExpandPanelButton={!isSmallAndUp}
  />
}

RecipePage.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
}

const useStyles = makeStyles(theme => ({
  expansionPanelDetailsRoot: {
    paddingTop: 0,
    paddingBottom: theme.spacing(1),
  },
  recipeTitle: {
    textTransform: 'capitalize',
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(1),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    fontSize: '1.4rem',
    fontWeight: '1000',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    color: 'white',
    width: '100%',
  },
}))

const RecipePageDisplay = ({
  recipe,
  onIngredientDeletion,
  onStepNoteSaveButtonClick,
  toggleFavorite,
  confirmDeleteRecipe,
  closeConfirmDeleteDialogOpen,
  isConfirmDeleteDialogOpen,
  isConverterOpen,
  closeConverter,
  notes,
  updateNotes,
  onPortionsUpdated,
  portionsUp,
  portionsDown,
  panelIngredientsExpanded,
  panelStepsExpanded,
  toggleIngredientsPanel,
  toggleStepsPanel,
  displayExpandPanelButton,
}) => {
  const classes = useStyles()

  const steps = recipe.steps
    .sort((a, b) => a.id > b.id ? 1 : -1)
    .map((step, index) => (
      <Grid item key={step.id} xs={12}>
        <Step
          step={step.name}
          note={step.note}
          onSaveNote={newNote => onStepNoteSaveButtonClick(step.id, newNote)}
          lastItem={index === recipe.steps.length - 1} />
      </Grid>))

  const capitalizedRecipeName = recipe.name.charAt(0).toUpperCase() + recipe.name.slice(1)
  const imgHeight = 200

  return <Grid container spacing={1}>
    <PageTitle title={recipe.name} />
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
    <Grid item xs={12} md={6}>
      <CardMedia image={recipe.imgUrl} style={{ height: imgHeight }} onError={onRecipeImageNotFoundError}>
        <Grid container style={{ height: imgHeight }} direction='column' justify='space-between' alignItems='flex-end'>
          <IconButton
            href=''
            onClick={toggleFavorite}
            style={{ color: recipe.favorite ? '#db2828' : 'white' }}
            data-toggle-favorite-button
          >
            {recipe.favorite ? <FavoriteIcon data-recipe-favorited fontSize='large' /> :
              <FavoriteBorderIcon fontSize='large' />}
          </IconButton>
          <Typography className={`${classes.recipeTitle} witchcraft-title`} data-recipe-title>
            {recipe.name}
          </Typography>
        </Grid>
      </CardMedia>
    </Grid>
    <Grid item xs={12} md={6}>
      <Grid container direction='row' spacing={1} alignContent='flex-start'>
        <Grid item xs={12}>
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
        <Grid item xs={12} container justify='flex-start' direction='row' alignItems='flex-start' spacing={1}>
          <Grid item>
            <PortionsContainer
              recipeId={recipe.id}
              portions={`${Number(recipe.portions)}`}
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
      </Grid>
    </Grid>
    <Grid item xs={12} sm={6}>
      <ExpansionPanel expanded={panelIngredientsExpanded} onChange={toggleIngredientsPanel}>
        <ExpansionPanelSummary
          expandIcon={displayExpandPanelButton ? <ExpandMoreIcon /> : undefined}
          aria-controls="panel-ingredients-content"
          id="panel-ingredients-header"
        >
          <Typography variant='h6' className='witchcraft-title'>Ingredients</Typography>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails classes={{ root: classes.expansionPanelDetailsRoot }}>
          <IngredientContainer ingredients={recipe.ingredients} onIngredientDeletion={onIngredientDeletion} />
        </ExpansionPanelDetails>
      </ExpansionPanel>
    </Grid>
    <Grid item xs={12} sm={6}>
      <ExpansionPanel expanded={panelStepsExpanded} onChange={toggleStepsPanel}>
        <ExpansionPanelSummary
          expandIcon={displayExpandPanelButton ? <ExpandMoreIcon /> : undefined}
          aria-controls="panel-steps-content"
          id="panel-steps-header"
        >
          <Typography variant='h6' className='witchcraft-title'>Steps</Typography>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails classes={{ root: classes.expansionPanelDetailsRoot }}>
          <List>
            {steps}
          </List>
        </ExpansionPanelDetails>
      </ExpansionPanel>
    </Grid>
    <ConverterContainer open={isConverterOpen} onClose={closeConverter} />
  </Grid>
}

RecipePageDisplay.propTypes = {
  recipe: PropTypes.object,
  onIngredientDeletion: PropTypes.func,
  onStepNoteSaveButtonClick: PropTypes.func,
  toggleFavorite: PropTypes.func,
  confirmDeleteRecipe: PropTypes.func,
  closeConfirmDeleteDialogOpen: PropTypes.func,
  isConfirmDeleteDialogOpen: PropTypes.bool,
  isConverterOpen: PropTypes.bool,
  closeConverter: PropTypes.func,
  notes: PropTypes.string,
  updateNotes: PropTypes.func,
  onPortionsUpdated: PropTypes.func,
  portionsUp: PropTypes.func,
  portionsDown: PropTypes.func,
  panelIngredientsExpanded: PropTypes.bool,
  panelStepsExpanded: PropTypes.bool,
  toggleIngredientsPanel: PropTypes.func,
  toggleStepsPanel: PropTypes.func,
  displayExpandPanelButton: PropTypes.bool,
}