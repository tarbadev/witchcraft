import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Button, Grid } from '@material-ui/core'
import CheckCircleIcon from '@material-ui/icons/CheckCircle'
import { addExpressRecipe } from 'src/weeks/actions/RecipeListModalActions'
import './RecipeListModal.css'
import { ExpressRecipeFormModalContainer } from './ExpressRecipeFormModal'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getAllRecipes } from 'src/recipes/actions/RecipesActions'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import { RecipeCard } from 'src/recipes/components/RecipeCard'

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
    currentRecipeIds={config.currentRecipeIds}
    setRecipe={setRecipe}
    addExpressRecipe={recipeName => {
      dispatch(addExpressRecipe(
        recipeName,
        recipe => setRecipe(recipe, config.day, config.meal),
      ))
      openExpressRecipeModal(false)
    }}
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
  currentRecipeIds,
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
      if (currentRecipeIds.includes(recipe?.id)) return setRecipe({ ...recipe, remove: true }, day, meal)
      else return setRecipe(recipe, day, meal)
    }
    const className = currentRecipeIds.includes(recipe?.id)
      ? 'current-recipe'
      : {}

    return (
      <Grid data-modal-recipe-container item xs={12} sm={4} key={`recipe-${recipe.id}`} onClick={onClick} style={{ position: 'relative' }}>
        <RecipeCard
          imgUrl={recipe.imgUrl}
          title={recipe.name} />
        {currentRecipeIds.includes(recipe?.id) && <CheckCircleIcon className={className} />}
      </Grid>
    )
  })

  return (
    <Dialog
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
      open={isModalOpen}
      onClose={closeModal}
      data-recipe-modal
      disableAutoFocus={true}>
      <DialogTitle id="customized-dialog-title" onClose={closeModal}>
        Choose a recipe for <span className={'day'}>{day}</span>{'\''}s {meal}
      </DialogTitle>
      <DialogContent dividers>
        <Grid container spacing={1}>
          {recipeCards}
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          className='week-page__add-express-recipe__button'
          onClick={displayExpressRecipeForm}
          variant='contained'
          href=''
          color='primary'>
          Add express recipe
        </Button>
        <ExpressRecipeFormModalContainer
          recipeName={expressRecipeName}
          onRecipeNameChange={setExpressRecipeName}
          isModalOpen={isDisplayExpressRecipeForm}
          onAddRecipeClick={addExpressRecipe}
          onClose={closeAddExpressRecipeForm} />
      </DialogActions>
    </Dialog>
  )
}

RecipeListModal.propTypes = {
  recipes: PropTypes.array,
  isModalOpen: PropTypes.bool,
  closeModal: PropTypes.func,
  day: PropTypes.string,
  meal: PropTypes.string,
  currentRecipeIds: PropTypes.array,
  displayExpressRecipeForm: PropTypes.func,
  isDisplayExpressRecipeForm: PropTypes.bool,
  expressRecipeName: PropTypes.string,
  setExpressRecipeName: PropTypes.func,
  addExpressRecipe: PropTypes.func,
  closeAddExpressRecipeForm: PropTypes.func,
  setRecipe: PropTypes.func,
}