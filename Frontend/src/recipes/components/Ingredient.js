import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'

import 'src/recipes/components/Ingredient.css'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import { useAppContext } from 'src/app/components/StoreProvider'
import { deleteIngredient, updateIngredient } from 'src/recipes/actions/RecipeActions'
import ClickAwayListener from '@material-ui/core/ClickAwayListener'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogActions from '@material-ui/core/DialogActions'
import Dialog from '@material-ui/core/Dialog'

export const IngredientContainer = ({ ingredient, index, recipeId, deleteCallback }) => {
  const { dispatch } = useAppContext()
  const [currentIngredient, setCurrentIngredient] = useState(null)
  const [isEditable, setEditable] = useState(false)
  const [isConfirmDeleteDialogOpen, setConfirmDeleteDialogOpen] = useState(false)
  const [editedIngredientName, setEditedIngredientName] = useState(null)
  const [editedIngredientQuantity, setEditedIngredientQuantity] = useState(null)
  const [editedIngredientUnit, setEditedIngredientUnit] = useState(null)

  useEffect(() => {
    setCurrentIngredient(ingredient)
  }, [ingredient])

  useEffect(() => {
    setEditedIngredientName(currentIngredient?.name)
  }, [currentIngredient?.name])

  useEffect(() => {
    setEditedIngredientQuantity(currentIngredient?.quantity)
  }, [currentIngredient?.quantity])

  useEffect(() => {
    setEditedIngredientUnit(currentIngredient?.unit)
  }, [currentIngredient?.unit])

  return <Ingredient
    quantity={currentIngredient?.quantity}
    unit={currentIngredient?.unit}
    ingredient={currentIngredient?.name}
    className={`ingredient_${index}`}
    editable={isEditable}
    showEditableMode={() => setEditable(true)}
    hideEditableMode={() => setEditable(false)}
    editedIngredientName={editedIngredientName}
    onIngredientNameChange={(newName) => setEditedIngredientName(newName)}
    editedIngredientQuantity={editedIngredientQuantity}
    onIngredientQuantityChange={(newQuantity) => setEditedIngredientQuantity(newQuantity)}
    editedIngredientUnit={editedIngredientUnit}
    onIngredientUnitChange={(newUnit) => setEditedIngredientUnit(newUnit)}
    onSaveClick={() => dispatch(updateIngredient(
      recipeId,
      { ...ingredient, name: editedIngredientName, quantity: editedIngredientQuantity, unit: editedIngredientUnit },
      (ingredient) => {
        setCurrentIngredient(ingredient)
        setEditable(false)
      },
    ))}
    displayConfirmDeleteDialog={() => setConfirmDeleteDialogOpen(true)}
    closeConfirmDeleteDialog={() => setConfirmDeleteDialogOpen(false)}
    isConfirmDeleteDialogOpen={isConfirmDeleteDialogOpen}
    deleteIngredient={() => dispatch(deleteIngredient(
      recipeId,
      ingredient.id,
      () => deleteCallback(),
      () => deleteCallback()))}
  />
}

IngredientContainer.propTypes = {
  ingredient: PropTypes.object,
  index: PropTypes.number,
  recipeId: PropTypes.number,
  deleteCallback: PropTypes.func,
}

export const Ingredient = ({
  quantity,
  unit,
  ingredient,
  className,
  editable,
  showEditableMode,
  hideEditableMode,
  editedIngredientName,
  onIngredientNameChange,
  editedIngredientQuantity,
  onIngredientQuantityChange,
  editedIngredientUnit,
  onIngredientUnitChange,
  onSaveClick,
  isConfirmDeleteDialogOpen,
  displayConfirmDeleteDialog,
  closeConfirmDeleteDialog,
  deleteIngredient,
}) => {
  let ingredientBody

  if (editable) {
    ingredientBody = (
      <ClickAwayListener onClickAway={hideEditableMode}>
        <Grid container data-ingredient-container spacing={1} alignItems='center'>
          <Grid item sm={6}>
            <TextField
              value={editedIngredientName}
              data-edit-name
              label='Name'
              fullWidth
              onChange={({ target }) => onIngredientNameChange(target.value)}
            />
          </Grid>
          <Grid item sm>
            <TextField
              value={editedIngredientQuantity}
              data-edit-quantity
              label='Quantity'
              fullWidth
              onChange={({ target }) => onIngredientQuantityChange(target.value)}
            />
          </Grid>
          <Grid item sm>
            <TextField
              value={editedIngredientUnit}
              data-edit-unit
              label='Unit'
              fullWidth
              onChange={({ target }) => onIngredientUnitChange(target.value)}
            />
          </Grid>
          <Grid item sm container direction='column' spacing={0}>
            <Grid item>
              <Button
                color='primary'
                href=''
                data-edit-save
                onMouseDown={onSaveClick}
              >
                Save
              </Button>
            </Grid>
            <Grid item>
              <Button
                color='secondary'
                href=''
                data-edit-delete
                onClick={displayConfirmDeleteDialog}
              >
                Delete
              </Button>
              <Dialog
                open={isConfirmDeleteDialogOpen}
                onClose={closeConfirmDeleteDialog}
              >
                <DialogTitle data-confirm-delete-title>
                  {`Delete ingredient ${ingredient}?`}
                </DialogTitle>
                <DialogActions>
                  <Button onClick={closeConfirmDeleteDialog} data-edit-cancel-delete autoFocus>
                    Cancel
                  </Button>
                  <Button onClick={deleteIngredient} data-edit-confirm-delete color='secondary'>
                    Delete
                  </Button>
                </DialogActions>
              </Dialog>
            </Grid>
          </Grid>
        </Grid>
      </ClickAwayListener>
    )
  } else {
    ingredientBody = (
      <Grid container data-ingredient-container onClick={showEditableMode}>
        <Grid item sm={10}>
          <Typography variant="body2" className='ingredient' data-name>
            {ingredient}
          </Typography>
        </Grid>
        <Grid item sm={2}>
          <Typography variant="body2" align="right" className='unit'>
            <span data-quantity="true">{quantity}</span> <span data-unit="true">{unit}</span>
          </Typography>
        </Grid>
      </Grid>
    )
  }

  return (
    <Paper elevation={1} className={`${className} paper`}>
      {ingredientBody}
    </Paper>
  )
}

Ingredient.propTypes = {
  quantity: PropTypes.number,
  unit: PropTypes.string,
  ingredient: PropTypes.string,
  className: PropTypes.string,
  editable: PropTypes.bool,
  showEditableMode: PropTypes.func,
  editedIngredientName: PropTypes.string,
  onIngredientNameChange: PropTypes.func,
  editedIngredientQuantity: PropTypes.number,
  onIngredientQuantityChange: PropTypes.func,
  editedIngredientUnit: PropTypes.string,
  onIngredientUnitChange: PropTypes.func,
  hideEditableMode: PropTypes.func,
  onSaveClick: PropTypes.func,
  isConfirmDeleteDialogOpen: PropTypes.bool,
  displayConfirmDeleteDialog: PropTypes.func,
  closeConfirmDeleteDialog: PropTypes.func,
  deleteIngredient: PropTypes.func,
}
