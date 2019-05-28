import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'
import { setState } from 'src/RootReducer'

import { updateRecipe } from 'src/recipes/actions/RecipeActions'

export const EditRecipePage = ({ changeFormInput, form, submitForm }) => {
  const onFormSubmit = () => {
    submitForm(form)
  }

  const ingredients = form.ingredients
    ? form.ingredients.map((ingredient, index) => (<Grid item xs={12} container key={index}>
      <Grid item xs={12}>
        <Typography variant='body1'>Ingredient {index + 1}</Typography>
      </Grid>
      <Grid item xs={2}>
        <TextField
          label='Quantity'
          className={`modify-form__ingredient-quantity-${index}`}
          fullWidth
          onChange={(e) => {
            changeFormInput(`editRecipe.form.ingredients.${index}.quantity`, e.target.value)
          }}
          value={ingredient.quantity}
          type='text' />
      </Grid>
      <Grid item xs={2}>
        <TextField
          label='Unit'
          className={`modify-form__ingredient-unit-${index}`}
          fullWidth
          onChange={(e) => {
            changeFormInput(`editRecipe.form.ingredients.${index}.unit`, e.target.value)
          }}
          value={ingredient.unit}
          type='text' />
      </Grid>
      <Grid item xs={8}>
        <TextField
          label='Name'
          className={`modify-form__ingredient-name-${index}`}
          fullWidth
          onChange={(e) => {
            changeFormInput(`editRecipe.form.ingredients.${index}.name`, e.target.value)
          }}
          value={ingredient.name}
          type='text' />
      </Grid>
    </Grid>))
    : undefined

  const steps = form.steps
    ? form.steps.map((step, index) => (<Grid item xs={12} container key={index}>
      <Grid item xs={12}>
        <Typography variant='body1'>Step {index + 1}</Typography>
        <TextField
          label='Name'
          className={`modify-form__step-name-${index}`}
          fullWidth
          onChange={(e) => {
            changeFormInput(`editRecipe.form.steps.${index}.name`, e.target.value)
          }}
          value={step.name}
          type='text' />
      </Grid>
    </Grid>))
    : undefined

  return (<Grid container spacing={24}>
    <Grid item xs={12}>
      <TextField
        label='Name'
        className='modify-form__name'
        fullWidth
        onChange={(e) => {
          changeFormInput('editRecipe.form.name', e.target.value)
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
          changeFormInput('editRecipe.form.url', e.target.value)
        }}
        value={form.url}
        placeholder='http://example.com/recipes/32434'
        type='text' />
    </Grid>
    <Grid item xs={6}>
      <TextField
        label='Image
        Url'
        className='modify-form__imgUrl'
        fullWidth
        onChange={(e) => {
          changeFormInput('editRecipe.form.imgUrl', e.target.value)
        }}
        value={form.imgUrl}
        placeholder='http://example.com/recipes/32434.png'
        type='text' />
    </Grid>
    {ingredients}
    {steps}
    <Grid item xs={12}>
      <Button variant='contained' className='modify-form__submit-button' color='primary' onClick={onFormSubmit}>
        Modify
      </Button>
    </Grid>
  </Grid>)
}

EditRecipePage.propTypes = {
  changeFormInput: PropTypes.func,
  form: PropTypes.object,
  submitForm: PropTypes.func,
}

const mapStateToProps = state => {
  return { form: state.editRecipe.form }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    changeFormInput: setState,
    submitForm: updateRecipe,
  }, dispatch)
}

export const EditRecipePageContainer = connect(mapStateToProps, mapDispatchToProps)(EditRecipePage)
