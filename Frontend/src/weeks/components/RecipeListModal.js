import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import {
  Button,
  CardMedia,
  Grid,
  GridList,
  GridListTile,
  GridListTileBar,
  ListSubheader,
  Modal,
  Paper,
  Typography,
} from '@material-ui/core'
import CheckCircleIcon from '@material-ui/icons/CheckCircle'
import { addExpressRecipe } from 'src/weeks/actions/RecipeListModalActions'
import './RecipeListModal.css'
import { ExpressRecipeFormModalContainer } from './ExpressRecipeFormModal'
import { onRecipeImageNotFoundError } from 'src/App'
import { useAppContext } from 'src/StoreProvider'
import { getAllRecipes } from 'src/recipes/actions/RecipesActions'

export const RecipeListModalContainer = ({ config, closeModal, setRecipe }) => {
  const { state, dispatch } = useAppContext()
  const [recipes, setRecipes] = useState(state.recipes)
  const [isExpressRecipeModalOpen, openExpressRecipeModal] = useState(state.pages.weekPage.modal.displayExpressRecipeForm)

  useEffect(() => dispatch(getAllRecipes(data => setRecipes(data.recipes))), [])

  return <RecipeListModal
    recipes={recipes}
    isModalOpen={config.isModalOpen}
    closeModal={closeModal}
    day={config.day}
    meal={config.meal}
    currentRecipeId={config.currentRecipeId}
    setRecipe={setRecipe}
    addExpressRecipe={recipeName => dispatch(addExpressRecipe(recipeName, recipe => setRecipe(recipe, config.day, config.meal)))}
    displayExpressRecipeForm={() => openExpressRecipeModal(true)}
    closeAddExpressRecipeForm={() => openExpressRecipeModal(false)}
    isDisplayExpressRecipeForm={isExpressRecipeModalOpen} />
}

RecipeListModalContainer.propTypes = {
  config: PropTypes.object,
  closeModal: PropTypes.func,
  setRecipe: PropTypes.func,
}

export const RecipeListModal = ({
  recipes = [],
  isModalOpen,
  closeModal,
  day,
  meal,
  currentRecipeId,
  displayExpressRecipeForm,
  closeAddExpressRecipeForm,
  isDisplayExpressRecipeForm,
  setExpressRecipeName,
  expressRecipeName,
  setRecipe,
  addExpressRecipe,
}) => {
  const recipeCards = recipes.map(recipe => {
    const onClick = () => {
      if (recipe?.id === currentRecipeId) return setRecipe({}, day, meal)
      else return setRecipe(recipe, day, meal)
    }
    const className = recipe?.id === currentRecipeId
      ? 'current-recipe'
      : {}
    const currentRecipeIcon = recipe?.id === currentRecipeId
      ? <CheckCircleIcon className={className} />
      : undefined
    return (
      <GridListTile key={recipe.imgUrl} className={'recipe-card'} onClick={onClick}>
        {recipe.imgUrl &&
        <CardMedia component='img' image={recipe.imgUrl} alt={recipe.name} onError={onRecipeImageNotFoundError} />}
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
                      href=''
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
        <ExpressRecipeFormModalContainer
          recipeName={expressRecipeName}
          onRecipeNameChange={setExpressRecipeName}
          isModalOpen={isDisplayExpressRecipeForm}
          onAddRecipeClick={addExpressRecipe}
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
  currentRecipeId: PropTypes.number,
  displayExpressRecipeForm: PropTypes.func,
  isDisplayExpressRecipeForm: PropTypes.bool,
  expressRecipeName: PropTypes.string,
  setExpressRecipeName: PropTypes.func,
  addExpressRecipe: PropTypes.func,
  closeAddExpressRecipeForm: PropTypes.func,
  setRecipe: PropTypes.func,
}