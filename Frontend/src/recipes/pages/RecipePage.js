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
import { initialState } from 'src/app/RootReducer'
import { capitalizeWords } from 'src/app/Utils'
import { StartCooking } from 'src/recipes/components/StartCooking'
import SvgIcon from '@material-ui/core/SvgIcon'

export const RecipePage = ({ match, history }) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  const theme = useTheme()
  const isSmallAndUp = useMediaQuery(theme.breakpoints.up('sm'))
  const [isConfirmDeleteDialogOpen, setIsConfirmDeleteDialogOpen] = useState(false)
  const [recipe, setRecipe] = useState(state.recipe)
  const [isConverterOpen, setConverterOpen] = useState(false)
  const [isStartCookingOpen, setStartCookingOpen] = useState(false)
  const [notes, setNotes] = useState('')
  const [panelIngredientsExpanded, setPanelIngredientsExpanded] = useState(isSmallAndUp)
  const [panelStepsExpanded, setPanelStepsExpanded] = useState(isSmallAndUp)
  const capitalizedRecipeName = capitalizeWords(recipe.name)

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
      ...initialState.headerConfig,
      currentLink: RECIPE,
      title: capitalizedRecipeName,
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
        <Divider key='menu-divider1' />,
        <MenuItem key='menu-converter' data-open-converter onClick={() => setConverterOpen(true)}>
          <ListItemIcon>
            <TransformIcon />
          </ListItemIcon>
          <ListItemText primary="Converter" />
        </MenuItem>,
        <Divider key='menu-divider2' />,
        <MenuItem key='menu-start-cooking' data-start-cooking-button onClick={() => setStartCookingOpen(true)}>
          <ListItemIcon>
            <SvgIcon viewBox='0 0 1000 1000'>
              <metadata> Svg Vector Icons : http://www.onlinewebfonts.com/icon</metadata>
              <g>
                <g transform="translate(0.000000,511.000000) scale(0.100000,-0.100000)">
                  <path
                    d="M4759.6,4982.4c-435.3-94-792.9-468-868.5-905.3c-20.4-118.5-30.7-145.1-57.2-136.9c-102.2,40.9-271.8,71.5-388.3,71.5c-502.7-2-917.5-369.9-980.9-874.6c-38.8-302.4,69.5-613,292.2-829.7c71.5-69.5,157.4-139,192.1-153.3c69.5-28.6,87.9-79.7,87.9-247.3c0-177.8-173.7-163.5,1965.8-163.5c1804.4,0,1892.3,2,1922.9,36.8c22.5,24.5,34.8,73.6,34.8,130.8c0,163.5,18.4,214.6,87.9,243.2c34.7,14.3,120.6,83.8,192.1,153.3c553.8,541.5,304.5,1475.4-445.5,1673.6c-183.9,49-445.5,32.7-631.4-40.9c-26.6-8.2-36.8,18.4-59.3,143c-71.5,435.3-433.2,807.2-874.6,899.1C5060,5019.2,4927.1,5019.2,4759.6,4982.4z" />
                  <path
                    d="M3660.2,1522.8c-310.6-4.1-431.2-12.3-443.4-30.7c-8.2-12.3-16.3-81.7-16.3-153.3c0-114.4-6.1-136.9-57.2-188c-32.7-30.6-69.5-94-81.7-136.9c-34.7-118.5-30.7-476.1,8.2-627.3c63.3-241.1,210.5-515,380.1-698.9c47-51.1,96-139,130.8-235c75.6-214.6,175.7-396.4,304.5-562c441.4-557.9,1107.6-737.7,1673.6-453.7c406.7,204.3,686.6,535.4,858.3,1015.6c34.7,96,83.8,183.9,130.8,235c271.8,294.3,412.8,649.8,412.8,1034c0,255.4-22.5,345.3-102.2,425c-53.1,55.2-59.3,75.6-65.4,220.7l-6.1,159.4H5438C4696.2,1529,3897.2,1526.9,3660.2,1522.8z" />
                  <path
                    d="M3190.2-1309.4c-566-232.9-987-425-1183.2-539.5c-222.7-128.7-539.5-443.4-672.3-670.3c-257.5-435.3-437.3-1032-445.5-1485.6c-4.1-204.3,6.1-216.6,306.5-320.8c408.7-145.1,886.9-251.3,1461.1-327c447.5-57.2,402.6-71.5,396.4,126.7l-6.1,169.6l-116.5,10.2c-132.8,12.3-210.5,69.5-237,171.7c-24.5,96,16.3,2141.6,42.9,2174.2c10.2,14.3,40.9,24.5,63.3,20.4c57.2-8.2,67.4-108.3,73.6-629.4c4.1-218.7,12.3-549.7,18.4-735.6l12.3-337.2h51.1h51.1l12.3,296.3c8.2,163.5,14.3,465.9,18.4,674.4c2,208.4,10.2,455.7,16.3,551.7c12.3,155.3,18.4,173.7,57.2,179.8c22.5,4.1,53.1-6.1,63.3-20.4c14.3-16.3,26.6-263.6,34.7-670.3c8.2-353.5,18.4-729.5,24.5-831.7c12.3-181.9,14.3-190.1,59.3-190.1c42.9,0,49,12.3,59.3,108.3c6.1,57.2,16.3,433.2,24.5,831.7c8.2,465.9,20.4,735.7,34.7,752c10.2,14.3,40.9,24.5,63.3,20.4c57.2-8.2,67.4-108.3,73.6-639.6c4.1-224.8,10.2-555.8,18.4-735.6l12.3-327h51.1h51.1l12.3,327c8.2,179.8,14.3,502.7,18.4,715.2c2,214.6,10.2,447.5,18.4,521.1c10.2,114.4,18.4,132.8,55.2,138.9c22.5,4.1,53.1-6.1,63.3-20.4c34.7-40.9,67.4-2112.9,34.7-2194.7c-36.8-98.1-120.6-151.2-235-151.2h-100.1v-192.1c0-167.5,4.1-194.1,36.8-204.3c18.4-6.1,314.7-20.4,658-34.7c582.4-22.5,1450.9-14.3,1855.5,16.3l167.6,12.2v200.3v198.2l-71.5,45c-173.7,108.3-341.3,361.7-423,643.7c-40.9,141-47,208.4-45,490.4c0,298.3,4.1,341.3,55.2,490.4c147.1,445.5,445.5,721.3,758.1,700.9c249.3-16.3,461.8-181.9,604.9-474.1c120.6-245.2,165.5-443.4,165.5-737.7c0-284-42.9-484.3-155.3-719.3c-71.5-155.3-214.6-324.9-329-396.4l-69.5-42.9V-4532c0-159.4,4.1-181.9,36.8-181.9c63.4,0,562,61.3,825.6,102.2c570.1,87.9,1322.1,294.3,1442.7,398.5c49.1,42.9,53.1,57.2,49.1,206.4c-14.3,465.9-190,1052.4-447.5,1487.7c-132.8,222.7-449.6,539.5-670.3,670.3c-190,114.4-631.4,312.7-1199.5,543.6l-402.6,165.5l-73.6-98.1c-106.3-147.1-331-351.5-508.8-461.8c-241.1-149.2-463.9-218.7-741.8-230.9c-284-12.3-465.9,26.6-715.2,147.1c-271.8,132.8-465.9,294.3-690.7,568.1l-61.3,73.6L3190.2-1309.4z" />
                </g>
              </g>
            </SvgIcon>
          </ListItemIcon>
          <ListItemText primary="Start Cooking" />
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
    capitalizedRecipeName={capitalizedRecipeName}
    isStartCookingOpen={isStartCookingOpen}
    closeStartCooking={() => setStartCookingOpen(false)}
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
    zIndex: 100,
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
  capitalizedRecipeName,
  isStartCookingOpen,
  closeStartCooking,
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
      <Grid
        style={{ height: imgHeight, position: 'relative' }}
        container
        direction='column'
        justify='space-between'
        alignItems='flex-end'
      >
        <CardMedia
          component='img'
          src={recipe.imgUrl}
          style={{ height: imgHeight, position: 'absolute', zIndex: 10 }}
          onError={onRecipeImageNotFoundError} />
        <IconButton
          href=''
          onClick={toggleFavorite}
          style={{ color: recipe.favorite ? '#db2828' : 'white', zIndex: 100 }}
          data-toggle-favorite-button
        >
          {recipe.favorite ? <FavoriteIcon data-recipe-favorited fontSize='large' /> :
            <FavoriteBorderIcon fontSize='large' />}
        </IconButton>
        <Typography className={`${classes.recipeTitle} witchcraft-title`} data-recipe-title>
          {recipe.name}
        </Typography>
      </Grid>
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
    <StartCooking open={isStartCookingOpen} recipe={recipe} onClose={closeStartCooking} />
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
  capitalizedRecipeName: PropTypes.string,
  isStartCookingOpen: PropTypes.bool,
  closeStartCooking: PropTypes.func,
}