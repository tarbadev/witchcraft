import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { makeStyles } from '@material-ui/core'
import ListItem from '@material-ui/core/ListItem'
import ListItemText from '@material-ui/core/ListItemText'
import Grid from '@material-ui/core/Grid'
import ClickAwayListener from '@material-ui/core/ClickAwayListener'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogActions from '@material-ui/core/DialogActions'
import { deleteIngredient, updateIngredient } from 'src/recipes/actions/RecipeActions'
import { useAppContext } from 'src/app/components/StoreProvider'

export const Ingredient = ({ ingredient, lastItem, recipeId, deleteCallback, index, editable = true }) => {
  const { dispatch } = useAppContext()
  const [isEditable, setEditable] = useState(false)
  const [currentIngredient, setCurrentIngredient] = useState(ingredient)
  const [editedIngredientName, setEditedIngredientName] = useState(ingredient.name)
  const [editedIngredientQuantity, setEditedIngredientQuantity] = useState(ingredient.quantity)
  const [editedIngredientUnit, setEditedIngredientUnit] = useState(ingredient.unit)
  const [isConfirmDeleteDialogOpen, setConfirmDeleteDialogOpen] = useState(false)

  useEffect(() => {
    setCurrentIngredient(ingredient)
  }, [ingredient])

  useEffect(() => {
    setEditedIngredientName(currentIngredient.name)
  }, [currentIngredient.name])

  useEffect(() => {
    setEditedIngredientQuantity(currentIngredient.quantity)
  }, [currentIngredient.quantity])

  useEffect(() => {
    setEditedIngredientUnit(currentIngredient.unit)
  }, [currentIngredient.unit])

  return <IngredientDisplay
    name={currentIngredient.name}
    quantity={`${currentIngredient.quantity} ${currentIngredient.unit}`}
    className={`ingredient_${index}`}
    lastItem={lastItem}
    editable={isEditable}
    showEditableMode={() => editable && setEditable(true)}
    hideEditableMode={() => setEditable(false)}
    editedIngredientName={editedIngredientName}
    onIngredientNameChange={(newName) => setEditedIngredientName(newName)}
    editedIngredientQuantity={editedIngredientQuantity}
    onIngredientQuantityChange={(newQuantity) => setEditedIngredientQuantity(newQuantity)}
    editedIngredientUnit={editedIngredientUnit}
    onIngredientUnitChange={(newUnit) => setEditedIngredientUnit(newUnit)}
    onSaveClick={() => dispatch(updateIngredient(
      recipeId,
      {
        ...ingredient,
        name: editedIngredientName,
        quantity: editedIngredientQuantity,
        unit: editedIngredientUnit
      },
      (ingredient) => {
        setCurrentIngredient(ingredient)
        setEditable(false)
      },
    ))}
    isConfirmDeleteDialogOpen={isConfirmDeleteDialogOpen}
    displayConfirmDeleteDialog={() => setConfirmDeleteDialogOpen(true)}
    closeConfirmDeleteDialog={() => setConfirmDeleteDialogOpen(false)}
    deleteIngredient={() => dispatch(deleteIngredient(
      recipeId,
      ingredient.id,
      () => deleteCallback(),
      () => deleteCallback()))}
  />
}

Ingredient.propTypes = {
  ingredient: PropTypes.object,
  lastItem: PropTypes.bool,
  recipeId: PropTypes.number,
  deleteCallback: PropTypes.func,
  index: PropTypes.number,
  editable: PropTypes.bool,
}

const useStyles = makeStyles({
  itemGutters: {
    padding: 0,
  },
})

export const IngredientDisplay = ({
  name,
  quantity,
  className,
  lastItem,
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
  displayConfirmDeleteDialog,
  isConfirmDeleteDialogOpen,
  closeConfirmDeleteDialog,
  deleteIngredient,
}) => {
  const classes = useStyles()
  let ingredientBody
  if (editable) {
    ingredientBody = <ClickAwayListener onClickAway={hideEditableMode}>
      <Grid container data-ingredient-container spacing={1} alignItems='center'>
        <Grid item xs={12} sm={6}>
          <TextField
            value={editedIngredientName}
            data-edit-name
            label='Name'
            fullWidth
            onChange={({ target }) => onIngredientNameChange(target.value)}
          />
        </Grid>
        <Grid item xs={6} sm={3}>
          <TextField
            value={editedIngredientQuantity}
            data-edit-quantity
            label='Quantity'
            fullWidth
            onChange={({ target }) => onIngredientQuantityChange(target.value)}
          />
        </Grid>
        <Grid item xs={6} sm={3}>
          <TextField
            value={editedIngredientUnit}
            data-edit-unit
            label='Unit'
            fullWidth
            onChange={({ target }) => onIngredientUnitChange(target.value)}
          />
        </Grid>
        <Grid item xs={12} container direction='row' alignItems='center' justify='space-between'>
          <Button
            color='secondary'
            href=''
            data-edit-delete
            onClick={displayConfirmDeleteDialog}
          >
            Delete
            <Dialog
              open={isConfirmDeleteDialogOpen}
              onClose={closeConfirmDeleteDialog}
            >
              <DialogTitle data-confirm-delete-title>
                {`Delete ingredient ${name}?`}
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
          </Button>
          <Button
            color='primary'
            href=''
            data-edit-save
            onMouseDown={onSaveClick}
          >
            Save
          </Button>
        </Grid>
      </Grid>
    </ClickAwayListener>
  } else {
    ingredientBody = <ListItemText
      data-ingredient-container
      primary={<span data-name='true'>{name}</span>}
      secondary={<span data-quantity='true'>{quantity}</span>}
      onClick={showEditableMode}
    />
  }

  return <ListItem
    classes={{ gutters: classes.itemGutters }}
    className={className}
    divider={!lastItem}
  >
    {ingredientBody}
  </ListItem>
}

IngredientDisplay.propTypes = {
  name: PropTypes.string,
  quantity: PropTypes.string,
  lastItem: PropTypes.bool,
  editable: PropTypes.bool,
  showEditableMode: PropTypes.func,
  hideEditableMode: PropTypes.func,
  editedIngredientName: PropTypes.string,
  onIngredientNameChange: PropTypes.func,
  editedIngredientQuantity: PropTypes.number,
  onIngredientQuantityChange: PropTypes.func,
  editedIngredientUnit: PropTypes.string,
  onIngredientUnitChange: PropTypes.func,
  onSaveClick: PropTypes.func,
  displayConfirmDeleteDialog: PropTypes.func,
  isConfirmDeleteDialogOpen: PropTypes.bool,
  closeConfirmDeleteDialog: PropTypes.func,
  deleteIngredient: PropTypes.func,
  className: PropTypes.string,
}