import React, { useState } from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'

import 'src/recipes/components/Ingredient.css'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import { useAppContext } from 'src/app/components/StoreProvider'
import { updateIngredient } from 'src/recipes/actions/RecipeActions'

export const IngredientContainer = ({ ingredient, index, recipeId }) => {
  const { dispatch } = useAppContext()
  const [currentIngredient, setCurrentIngredient] = useState(ingredient)
  const [isEditable, setEditable] = useState(false)
  const [editedIngredientName, setEditedIngredientName] = useState(currentIngredient.name)
  const [editedIngredientQuantity, setEditedIngredientQuantity] = useState(currentIngredient.quantity)
  const [editedIngredientUnit, setEditedIngredientUnit] = useState(currentIngredient.unit)

  return <Ingredient
    quantity={currentIngredient.quantity}
    unit={currentIngredient.unit}
    ingredient={currentIngredient.name}
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
    onSaveClick={() => dispatch(updateIngredient(recipeId,
      { ...ingredient, name: editedIngredientName, quantity: editedIngredientQuantity, unit: editedIngredientUnit },
      (ingredient) => {
        setCurrentIngredient(ingredient)
        setEditable(false)
      },
    ))}
  />
}

IngredientContainer.propTypes = {
  ingredient: PropTypes.object,
  index: PropTypes.number,
  recipeId: PropTypes.number,
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
}) => {
  let ingredientBody

  if (editable) {
    ingredientBody = (
      <Grid container data-ingredient-container onBlur={hideEditableMode}>
        <Grid item container sm={12} spacing={1} alignItems='center'>
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
          <Grid item sm>
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
      </Grid>
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
}
