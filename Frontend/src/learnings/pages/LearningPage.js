import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getIngredientsToValidate } from 'src/learnings/actions/LearningActions'
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

  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: LEARNING,
    title: LEARNING,
  }), [])
  useEffect(() => dispatch(getIngredientsToValidate(ingredients => setIngredientsToValidate(ingredients))), [])

  return <LearningDisplayPage
    ingredientsToValidate={ingredientsToValidate}
  />
}

const LearningDisplayPage = ({
  ingredientsToValidate,
}) => {
  const ingredientLearnings = ingredientsToValidate.map(ingredient =>
    <Grid item xs={12} key={ingredient.id}>
      <Paper style={{ padding: 10 }}>
        <Grid container spacing={1} data-ingredient alignItems='flex-end'>
          <Grid item xs={12}>
            <TextField label='Line' value={ingredient.line} fullWidth disabled data-ingredient-line />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField label='Name' value={ingredient.name} fullWidth data-ingredient-name />
          </Grid>
          <Grid item xs={4} sm={2}>
            <TextField label='Quantity' value={ingredient.quantity} fullWidth data-ingredient-quantity />
          </Grid>
          <Grid item xs={4} sm={2}>
            <TextField label='Unit' value={ingredient.unit} fullWidth data-ingredient-unit />
          </Grid>
          <Grid item xs={4} sm={2}>
            <Select label='Language' value={ingredient.language} fullWidth data-ingredient-language>
              <MenuItem value='ENGLISH'>English</MenuItem>
              <MenuItem value='FRENCH'>French</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12} sm>
            <Button variant='contained' color='primary' style={{ float: 'right' }}>Validate</Button>
          </Grid>
        </Grid>
      </Paper>
    </Grid>,
  )

  return <Grid container spacing={1}>
    {ingredientLearnings}
  </Grid>
}

LearningDisplayPage.propTypes = {
  ingredientsToValidate: PropTypes.array,
}