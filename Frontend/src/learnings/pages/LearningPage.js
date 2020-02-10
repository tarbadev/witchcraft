import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getIngredientsToValidate, validateIngredient } from 'src/learnings/actions/LearningActions'
import { initialState } from 'src/app/RootReducer'
import { LEARNING } from 'src/app/components/Header'
import TextField from '@material-ui/core/TextField'
import Select from '@material-ui/core/Select'
import MenuItem from '@material-ui/core/MenuItem'
import Button from '@material-ui/core/Button'
import Paper from '@material-ui/core/Paper'

export const LearningPage = () => {
  const { dispatch, setHeaderConfig } = useAppContext()
  const [ingredientsToValidate, setIngredientsToValidate] = useState([])

  const loadLearningIngredients = () => dispatch(
    getIngredientsToValidate(ingredients => setIngredientsToValidate(ingredients))
  )

  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: LEARNING,
    title: LEARNING,
  }), [])
  useEffect(loadLearningIngredients, [])

  return <LearningDisplayPage
    ingredientsToValidate={ingredientsToValidate.filter(ingredient => !ingredient.valid)}
    validateIngredient={(id, newIngredient) => dispatch(validateIngredient(id, newIngredient, loadLearningIngredients, loadLearningIngredients))}
  />
}

const LearningDisplayPage = ({
  ingredientsToValidate,
  validateIngredient,
}) => {
  const ingredientLearnings = ingredientsToValidate.map(ingredient => (
    <LearningIngredient key={ingredient.id} ingredient={ingredient} validateIngredient={validateIngredient} />
  ))

  return <Grid container spacing={1}>
    {ingredientLearnings}
  </Grid>
}

LearningDisplayPage.propTypes = {
  ingredientsToValidate: PropTypes.array,
  validateIngredient: PropTypes.func,
}

const LearningIngredient = ({ ingredient, validateIngredient }) => {
  const [editName, setEditName] = useState(ingredient.name)
  const [editQuantity, setEditQuantity] = useState(ingredient.quantity)
  const [editUnit, setEditUnit] = useState(ingredient.unit)
  const [editLanguage, setEditLanguage] = useState(ingredient.language)

  return <LearningIngredientDisplay
    line={ingredient.line}
    name={editName}
    updateName={newName => setEditName(newName)}
    quantity={editQuantity}
    updateQuantity={newQuantity => setEditQuantity(newQuantity)}
    unit={editUnit}
    updateUnit={newUnit => setEditUnit(newUnit)}
    language={editLanguage}
    updateLanguage={newLanguage => setEditLanguage(newLanguage)}
    validateIngredient={() => validateIngredient(
      ingredient.id,
      { name: editName, quantity: editQuantity, unit: editUnit, language: editLanguage },
    )}
  />
}

LearningIngredient.propTypes = {
  ingredient: PropTypes.object,
  validateIngredient: PropTypes.func,
}

const LearningIngredientDisplay = ({
  line,
  name,
  updateName,
  quantity,
  updateQuantity,
  unit,
  updateUnit,
  language,
  updateLanguage,
  validateIngredient,
}) => {
  return (
    <Grid item xs={12}>
      <Paper style={{ padding: 10 }}>
        <Grid container spacing={1} data-ingredient alignItems='flex-end'>
          <Grid item xs={12}>
            <TextField
              label='Line'
              value={line}
              fullWidth
              disabled
              data-ingredient-line />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              label='Name'
              value={name}
              fullWidth
              data-ingredient-name
              onChange={({ target }) => updateName(target.value)} />
          </Grid>
          <Grid item xs={4} sm={2}>
            <TextField
              label='Quantity'
              value={quantity}
              fullWidth
              data-ingredient-quantity
              onChange={({ target }) => updateQuantity(target.value)} />
          </Grid>
          <Grid item xs={4} sm={2}>
            <TextField
              label='Unit'
              value={unit}
              fullWidth
              data-ingredient-unit
              onChange={({ target }) => updateUnit(target.value)} />
          </Grid>
          <Grid item xs={4} sm={2}>
            <Select
              label='Language'
              value={language}
              fullWidth
              data-ingredient-language
              onChange={({ target }) => updateLanguage(target.value)}>
              <MenuItem value='ENGLISH'>English</MenuItem>
              <MenuItem value='FRENCH'>French</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12} sm>
            <Button
              variant='contained'
              color='primary'
              style={{ float: 'right' }}
              data-ingredient-validate
              onClick={validateIngredient}
            >
              Validate
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Grid>
  )
}

LearningIngredientDisplay.propTypes = {
  line: PropTypes.string,
  name: PropTypes.string,
  updateName: PropTypes.func,
  quantity: PropTypes.number,
  updateQuantity: PropTypes.func,
  unit: PropTypes.string,
  updateUnit: PropTypes.func,
  language: PropTypes.string,
  updateLanguage: PropTypes.func,
  validateIngredient: PropTypes.func,
}