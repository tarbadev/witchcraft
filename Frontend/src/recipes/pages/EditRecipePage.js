import React, { useEffect, useReducer, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'

import { getRecipe, updateRecipe } from 'src/recipes/actions/RecipeActions'
import { PageTitle } from 'src/app/components/PageTitle'
import { reducer, setState } from 'src/app/RootReducer'
import { useAppContext } from 'src/app/components/StoreProvider'
import { RECIPE } from 'src/app/components/Header'

export const EditRecipePageContainer = ({ match, history }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(RECIPE)
  const [recipe, setRecipe] = useState(state.recipe)

  useEffect(() =>
    dispatch(getRecipe(
      match.params.id,
      recipeToEdit => setRecipe(recipeToEdit),
      () => history.push('/recipes'),
    )), [])

  return <EditRecipePage
    recipe={recipe}
    submitForm={form => dispatch(updateRecipe(form, newRecipe => history.push(`/recipes/${newRecipe.id}`)))} />
}

EditRecipePageContainer.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
}

export const EditRecipePage = ({ recipe, submitForm }) => {
  if (recipe.id <= 0) return null

  const [form, dispatch] = useReducer(reducer, recipe)

  const onFormSubmit = () => {
    submitForm({ ...form, url: form.originUrl, originUrl: undefined })
  }

  const steps = form.steps
    ? form.steps.map((step, index) =>
      (<Grid item xs={12} container key={index}>
        <Grid item xs={12}>
          <Typography variant='body1'>Step {index + 1}</Typography>
          <TextField
            label='Name'
            className={`modify-form__step-name-${index}`}
            fullWidth
            onChange={(e) => {
              dispatch(setState(`steps.${index}.name`, e.target.value))
            }}
            value={step.name}
            type='text' />
        </Grid>
      </Grid>))
    : undefined

  return (
    <Grid container spacing={3}>
      <PageTitle title={`Edit ${recipe.name}`} />
      <Grid item xs={12}>
        <TextField
          label='Name'
          className='modify-form__name'
          fullWidth
          onChange={(e) => {
            dispatch(setState('name', e.target.value))
          }}
          value={form.name}
          placeholder='Mini Goat Cheese Stuffed Potato Appetizers'
          type='text' />
      </Grid>
      <Grid item xs={6}>
        <TextField
          label='Url'
          className='modify-form__url'
          fullWidth
          onChange={(e) => {
            dispatch(setState('originUrl', e.target.value))
          }}
          value={form.originUrl}
          placeholder='http://example.com/recipes/32434'
          type='text' />
      </Grid>
      <Grid item xs={6}>
        <TextField
          label='Image Url'
          className='modify-form__imgUrl'
          fullWidth
          onChange={(e) => {
            dispatch(setState('imgUrl', e.target.value))
          }}
          value={form.imgUrl}
          placeholder='http://example.com/recipes/32434.png'
          type='text' />
      </Grid>
      {steps}
      <Grid item xs={12}>
        <Button
          variant='contained'
          className='modify-form__submit-button'
          color='primary'
          href=''
          onClick={onFormSubmit}>
          Modify
        </Button>
      </Grid>
    </Grid>
  )
}

EditRecipePage.propTypes = {
  recipe: PropTypes.object,
  submitForm: PropTypes.func,
}