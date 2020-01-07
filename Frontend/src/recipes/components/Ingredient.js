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

  return <Ingredient
    quantity={currentIngredient.quantity}
    unit={currentIngredient.unit}
    ingredient={currentIngredient.name}
    className={`ingredient_${index}`}
    editable={isEditable}
    editedIngredientName={editedIngredientName}
    showEditableMode={() => setEditable(true)}
    hideEditableMode={() => setEditable(false)}
    onIngredientNameChange={(newName) => setEditedIngredientName(newName)}
    onSaveClick={() => dispatch(updateIngredient(recipeId,
      { ...ingredient, name: editedIngredientName },
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
  editedIngredientName,
  showEditableMode,
  onIngredientNameChange,
  hideEditableMode,
  onSaveClick,
}) => {
  let ingredientBody

  if (editable) {
    ingredientBody = (
      <Grid container autoFocus data-ingredient-container onBlur={hideEditableMode}>
        <Grid item sm={10}>
          <TextField
            value={editedIngredientName}
            data-edit-name
            onChange={({ target }) => onIngredientNameChange(target.value)}
          />
        </Grid>
        <Grid item sm={2}>
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
  editedIngredientName: PropTypes.string,
  editable: PropTypes.bool,
  showEditableMode: PropTypes.func,
  onIngredientNameChange: PropTypes.func,
  hideEditableMode: PropTypes.func,
  onSaveClick: PropTypes.func,
}
