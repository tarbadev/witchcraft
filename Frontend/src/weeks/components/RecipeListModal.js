import React from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import PropTypes from 'prop-types'
import {
  Modal,
  Paper,
  Grid,
  GridList,
  GridListTileBar,
  GridListTile,
  ListSubheader,
  Button,
  CardMedia,
  Typography,
} from '@material-ui/core'
import CheckCircleIcon from '@material-ui/icons/CheckCircle'

import { toggleModal } from 'src/weeks/actions/WeekPageActions'
import { setRecipe, addExpressRecipe } from 'src/weeks/actions/RecipeListModalActions'
import { setState } from 'src/RootReducer'
import './RecipeListModal.css'
import { ExpressRecipeFormModal } from './ExpressRecipeFormModal'
import { onRecipeImageNotFoundError } from 'src/App'

export const RecipeListModal = ({
  recipes = [],
  isModalOpen,
  closeModal,
  day,
  meal,
  onRecipeClick,
  currentRecipeId,
  displayExpressRecipeForm,
  isDisplayExpressRecipeForm,
  setExpressRecipeName,
  expressRecipeName,
  addExpressRecipe,
  closeAddExpressRecipeForm,
}) => {
  const recipeCards = recipes.map(recipe => {
    const onClick = () => {
      if (recipe?.id === currentRecipeId) return onRecipeClick({}, day, meal)
      else return onRecipeClick(recipe, day, meal)
    }
    const className = recipe?.id === currentRecipeId
      ? 'current-recipe'
      : {}
    const currentRecipeIcon = recipe?.id === currentRecipeId
      ? <CheckCircleIcon className={className} />
      : undefined
    return (
      <GridListTile key={recipe.imgUrl} className={'recipe-card'} onClick={onClick}>
        {recipe.imgUrl && <CardMedia component='img' image={recipe.imgUrl} alt={recipe.name} onError={onRecipeImageNotFoundError} />}
        <GridListTileBar title={recipe.name} className={'recipe-card-title'} />
        {currentRecipeIcon && currentRecipeIcon}
      </GridListTile>
    )
  })

  return (
    <Modal
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
      open={isModalOpen}
      onClose={closeModal}
      disableAutoFocus={true}>
      <Grid container justify='center' alignItems='center' direction='row' className={'modal'}>
        <Grid item xs={10}>
          <Paper>
            <GridList cellHeight={180} cols={4}>
              <GridListTile key="Subheader" cols={4} className={'recipe-cards-title'}>
                <ListSubheader component="div">
                  <Grid container justify='space-between' alignItems='center'>
                    <Typography>Choose a recipe for <span className={'day'}>{day}</span>{'\''}s {meal}</Typography>
                    <Button
                      className='week-page__add-express-recipe__button'
                      onClick={displayExpressRecipeForm}
                      variant='contained'
                      color='primary'>
                      Add express recipe
                    </Button>
                  </Grid>
                </ListSubheader>
              </GridListTile>
              {recipeCards}
            </GridList>
          </Paper>
        </Grid>
        <ExpressRecipeFormModal
          recipeName={expressRecipeName}
          onRecipeNameChange={setExpressRecipeName}
          isModalOpen={isDisplayExpressRecipeForm}
          onAddRecipeClick={recipeName => addExpressRecipe(recipeName, day, meal)}
          onClose={closeAddExpressRecipeForm} />
      </Grid>
    </Modal>
  )
}

RecipeListModal.propTypes = {
  recipes: PropTypes.array,
  isModalOpen: PropTypes.bool,
  closeModal: PropTypes.func,
  day: PropTypes.string,
  meal: PropTypes.string,
  onRecipeClick: PropTypes.func,
  currentRecipeId: PropTypes.number,
  displayExpressRecipeForm: PropTypes.func,
  isDisplayExpressRecipeForm: PropTypes.bool,
  expressRecipeName: PropTypes.string,
  setExpressRecipeName: PropTypes.func,
  addExpressRecipe: PropTypes.func,
  closeAddExpressRecipeForm: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    recipes: state.app.recipes,
    isModalOpen: state.app.weekPage.modal.isModalOpen,
    day: state.app.weekPage.modal.day,
    meal: state.app.weekPage.modal.meal,
    currentRecipeId: state.app.weekPage.modal.currentRecipeId,
    expressRecipeName: state.app.expressRecipeForm.recipeName,
    isDisplayExpressRecipeForm: state.app.weekPage.modal.displayExpressRecipeForm,
  }
}

const mapDispatchToProps = dispatch => {
  return bindActionCreators({
    closeModal: () => toggleModal(false),
    onRecipeClick: setRecipe,
    displayExpressRecipeForm: () => setState('weekPage.modal.displayExpressRecipeForm', true),
    closeAddExpressRecipeForm: () => setState('weekPage.modal.displayExpressRecipeForm', false),
    setExpressRecipeName: recipeName => setState('expressRecipeForm.recipeName', recipeName),
    addExpressRecipe: addExpressRecipe,
  }, dispatch)
}

export const RecipeListModalContainer = connect(mapStateToProps, mapDispatchToProps)(RecipeListModal)
