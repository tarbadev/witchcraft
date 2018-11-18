import React from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import PropTypes from 'prop-types'
import Modal from '@material-ui/core/Modal'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'
import GridList from '@material-ui/core/GridList'
import GridListTileBar from '@material-ui/core/GridListTileBar'
import GridListTile from '@material-ui/core/GridListTile'
import ListSubheader from '@material-ui/core/ListSubheader'
import CheckCircleIcon from '@material-ui/icons/CheckCircle'

import { toggleModal } from 'app-actions/WeekPageActions'
import { setRecipe } from 'app-actions/RecipeListModalActions'
import styles from './RecipeListModal.css'

export const RecipeListModal = ({
  recipes,
  isModalOpen,
  closeModal,
  day,
  meal,
  onRecipeClick,
  currentRecipeId,
}) => {
  const recipeCards = recipes.map(recipe => {
    const onClick = () => {
      if (recipe?.id == currentRecipeId) return onRecipeClick({}, day, meal)
      else return onRecipeClick(recipe, day, meal)
    }
    const className = recipe?.id == currentRecipeId
      ? styles.currentRecipe
      : {}
    const currentRecipeIcon = recipe?.id == currentRecipeId
      ? <CheckCircleIcon className={className} />
      : undefined
    return (
      <GridListTile key={recipe.imgUrl} className={styles.recipeCard} onClick={onClick} >
        <img src={recipe.imgUrl} alt={recipe.name} />
        <GridListTileBar title={recipe.name} className={styles.recipeCardTitle} />
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
      disableAutoFocus={true} >
      <Grid container justify='center' alignItems='center' direction='row' className={styles.modal}>
        <Grid item xs={10}>
          <Paper>
            <GridList cellHeight={180} cols={4}>
              <GridListTile key="Subheader" cols={4} className={styles.recipeCardsTitle} >
                <ListSubheader component="div">
                  Choose a recipe for <span className={styles.day}>{day}</span>{'\''}s {meal}
                </ListSubheader>
              </GridListTile>
              {recipeCards}
            </GridList>
          </Paper>
        </Grid>
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
}

const mapStateToProps = state => {
  return {
    recipes: state.recipes,
    isModalOpen: state.weekPage.modal.isModalOpen,
    day: state.weekPage.modal.day,
    meal: state.weekPage.modal.meal,
    currentRecipeId: state.weekPage.modal.currentRecipeId,
  }
}

const mapDispatchToProps = dispatch => {
  return bindActionCreators({
    closeModal: () => toggleModal(false),
    onRecipeClick: setRecipe,
  }, dispatch)
}

export const RecipeListModalContainer = connect(mapStateToProps, mapDispatchToProps)(RecipeListModal)
