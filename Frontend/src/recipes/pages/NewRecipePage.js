import React from 'react'
import PropTypes from 'prop-types'
import {connect} from 'react-redux'
import {bindActionCreators} from 'redux'
import Grid from '@material-ui/core/Grid'
import Paper from '@material-ui/core/Paper'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'
import { withStyles } from '@material-ui/core/styles'
import { setState } from 'src/RootReducer'

import { formInputChange, submitForm } from 'src/recipes/actions/NewRecipeActions'

const styles = () => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: '.5em 1em !important'
  }
})

export const NewRecipePage = ({
  classes,
  changeFormInput,
  submitForm,
  autoUrl = {},
  manualUrl = {},
  redirect,
  history,
  setState
}) => {
  const onUrlFormSubmit = () => {
    submitForm('/api/recipes/importFromUrl', autoUrl)
  }

  const onManualUrlFormSubmit = () => {
    submitForm('/api/recipes/importFromForm', manualUrl)
  }

  if (redirect) {
    history.push('/recipes')
    setState('newRecipe.forms', { recipeAdded: false, autoUrl: {}, manualUrl: {} })
  }

  return (
    <Grid container spacing={24}>
      <Grid item xs={12}>
        <Typography variant='display1'>New Recipe</Typography>
      </Grid>
      <Grid item xs={6}>
        <Paper className={classes.paper}>
          <form>
            <Grid container spacing={24}>
              <Grid item xs={12} >
                <Typography variant='title'>Add manually</Typography>
              </Grid>
              <Grid item xs={12} >
                <TextField
                  label='Name'
                  className='manual__name'
                  fullWidth
                  onChange={(e) => {changeFormInput('manualUrl.name', e.target.value)}}
                  value={manualUrl.name}
                  placeholder='Mini Goat Cheese Stuffed Potato Appetizers'
                  type='text' />
                <TextField
                  label='Url'
                  className='manual__url'
                  fullWidth
                  onChange={(e) => {changeFormInput('manualUrl.url', e.target.value)}}
                  value={manualUrl.url}
                  placeholder='http://example.com/recipe/32434'
                  type='text' />
                <TextField
                  label='Image Url'
                  className='manual__imageUrl'
                  fullWidth
                  onChange={(e) => {changeFormInput('manualUrl.imageUrl', e.target.value)}}
                  value={manualUrl.imageUrl}
                  placeholder='http://example.com/recipe/32434.png'
                  type='text' />
                <TextField
                  label='Ingredients'
                  className='manual__ingredients'
                  multiline
                  fullWidth
                  onChange={(e) => {changeFormInput('manualUrl.ingredients', e.target.value)}}
                  value={manualUrl.ingredients}
                  placeholder='2 oz. soft goat cheese'
                  type='text' />
                <TextField
                  label='Steps'
                  className='manual__steps'
                  multiline
                  fullWidth
                  onChange={(e) => {changeFormInput('manualUrl.steps', e.target.value)}}
                  value={manualUrl.steps}
                  placeholder='Test\nTest'
                  type='text' />
              </Grid>
              <Grid item xs={12} >
                <Button variant='contained' className='manual__submit-button' color='primary' onClick={onManualUrlFormSubmit}>
                  Submit
                </Button>
              </Grid>
            </Grid>
          </form>
        </Paper>
      </Grid>
      <Grid item xs={6}>
        <Paper className={classes.paper}>
          <Grid container spacing={24}>
            <Grid item xs={12} >
              <Typography variant='title'>Add from URL</Typography>
            </Grid>
            <Grid item xs={12} >
              <TextField
                fullWidth
                className='auto__url'
                onChange={(e) => {changeFormInput('autoUrl.url', e.target.value)}}
                value={autoUrl.url}
                label='http://example.com/recipe/32434'
                type='search' />
            </Grid>
            <Grid item xs={12} >
              <Button variant='contained' className='auto__submit-button' color='primary' onClick={onUrlFormSubmit}>
                Submit
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Grid>
  )
}

NewRecipePage.propTypes = {
  classes: PropTypes.object,
  changeFormInput: PropTypes.func,
  submitForm: PropTypes.func,
  autoUrl: PropTypes.object,
  manualUrl: PropTypes.object,
  redirect: PropTypes.bool,
  history: PropTypes.object,
  setState: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    autoUrl: state.newRecipe.forms.autoUrl,
    manualUrl: state.newRecipe.forms.manualUrl,
    redirect: state.newRecipe.forms.recipeAdded
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    changeFormInput: formInputChange,
    submitForm: submitForm,
    setState: setState
  },
  dispatch
  )
}

const NewRecipePageWithStyles = withStyles(styles)(NewRecipePage)

export const NewRecipePageContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(NewRecipePageWithStyles)