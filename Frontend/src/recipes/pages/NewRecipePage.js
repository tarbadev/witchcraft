import React, { useState } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { Button, Grid, makeStyles, Paper, TextField, Typography } from '@material-ui/core'
import { setState } from 'src/RootReducer'
import { submitForm } from 'src/recipes/actions/NewRecipeActions'

import './NewRecipePage.css'
import GridList from '@material-ui/core/GridList'
import GridListTile from '@material-ui/core/GridListTile'
import { PageTitle } from '../../PageTitle'

const useStyles = makeStyles({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: '.5em 1em !important',
  },
})

export const NewRecipePage = ({
  submitForm,
  redirect,
  history,
  setState,
  supportedDomains = [],
}) => {
  const [urlFormValue, setUrlFormValue] = useState('')
  const [manualFormName, setManualFormName] = useState('')
  const [manualFormUrl, setManualFormUrl] = useState('')
  const [manualFormImageUrl, setManualFormImageUrl] = useState('')
  const [manualFormIngredients, setManualFormIngredients] = useState('')
  const [manualFormSteps, setManualFormSteps] = useState('')
  const [manualFormPortions, setManualFormPortions] = useState('')
  const classes = useStyles()
  const onUrlFormSubmit = () => {
    submitForm('/api/recipes/import-from-url', { url: urlFormValue })
  }

  const onManualUrlFormSubmit = () => {
    const manualForm = {
      name: manualFormName,
      url: manualFormUrl,
      imageUrl: manualFormImageUrl,
      ingredients: manualFormIngredients,
      steps: manualFormSteps,
      portions: manualFormPortions,
    }
    submitForm('/api/recipes/import-from-form', manualForm)
  }

  if (redirect) {
    history.push('/recipes')
    setState('pages.newRecipePage.forms', { recipeAdded: false })
  }

  const supportedDomainList = supportedDomains.map((domain) => (
    <GridListTile key={domain.name}>
      <Button href={domain.url} rel='noopener noreferrer' target='_blank' className='supported-domains__domain'>
        <img src={domain.imgUrl} title={domain.name} className='supported-domains__domain-image' alt={domain.name} />
      </Button>
    </GridListTile>
  ))

  return (
    <Grid container spacing={3} justify='flex-start' alignItems='flex-start'>
      <PageTitle title='New Recipe' />
      <Grid item xs={12}>
        <Typography variant='h4'>New Recipe</Typography>
      </Grid>
      <Grid item xs={6}>
        <Paper className={classes.paper}>
          <form>
            <Grid container spacing={3} justify='flex-start' alignItems='flex-start'>
              <Grid item xs={12}>
                <Typography variant='h6'>Add manually</Typography>
              </Grid>
              <Grid item xs={12}>
                <Grid container direction='row'>
                  <Grid item xs={9}>
                    <TextField
                      label='Name'
                      className='manual__name'
                      fullWidth
                      onChange={(e) => setManualFormName(e.target.value)}
                      value={manualFormName}
                      placeholder='Mini Goat Cheese Stuffed Potato Appetizers'
                      type='text' />
                  </Grid>
                  <Grid item xs={3}>
                    <TextField
                      label='Portions'
                      className='manual__portions'
                      fullWidth
                      onChange={(e) => setManualFormPortions(e.target.value)}
                      value={manualFormPortions}
                      placeholder='4'
                      type='text' />
                  </Grid>
                </Grid>
                <TextField
                  label='Url'
                  className='manual__url'
                  fullWidth
                  onChange={(e) => setManualFormUrl(e.target.value)}
                  value={manualFormUrl}
                  placeholder='http://example.com/recipe/32434'
                  type='text' />
                <TextField
                  label='Image Url'
                  className='manual__image-url'
                  fullWidth
                  onChange={(e) => setManualFormImageUrl(e.target.value)}
                  value={manualFormImageUrl}
                  placeholder='http://example.com/recipe/32434.png'
                  type='text' />
                <TextField
                  label='Ingredients'
                  className='manual__ingredients'
                  name='ingredients'
                  multiline
                  fullWidth
                  onChange={(e) => setManualFormIngredients(e.target.value)}
                  value={manualFormIngredients}
                  placeholder='2 oz. soft goat cheese'
                  type='text' />
                <TextField
                  label='Steps'
                  className='manual__steps'
                  name='steps'
                  multiline
                  fullWidth
                  onChange={(e) => setManualFormSteps(e.target.value)}
                  value={manualFormSteps}
                  placeholder='Add ingredients and stir\nServe on each plate'
                  type='text' />
              </Grid>
              <Grid item xs={12}>
                <Button
                  variant='contained'
                  className='manual__submit-button'
                  color='primary'
                  href=''
                  onClick={onManualUrlFormSubmit}>
                  Submit
                </Button>
              </Grid>
            </Grid>
          </form>
        </Paper>
      </Grid>
      <Grid item container spacing={1} xs={6} justify='flex-start' alignItems='flex-start'>
        <Grid item xs={12}>
          <Paper className={classes.paper}>
            <Grid container spacing={3} justify='flex-start' alignItems='flex-start'>
              <Grid item xs={12}>
                <Typography variant='h6'>Add from URL</Typography>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  className='auto__url'
                  onChange={(e) => setUrlFormValue(e.target.value)}
                  value={urlFormValue}
                  label='http://example.com/recipe/32434'
                  type='search' />
              </Grid>
              <Grid item xs={12}>
                <Button
                  variant='contained'
                  className='auto__submit-button'
                  color='primary'
                  href=''
                  onClick={onUrlFormSubmit}>
                  Submit
                </Button>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
        <GridList cellHeight={50} cols={2} className='supported-domains'>
          {supportedDomainList}
        </GridList>
      </Grid>
    </Grid>
  )
}

NewRecipePage.propTypes = {
  classes: PropTypes.object,
  submitForm: PropTypes.func,
  redirect: PropTypes.bool,
  history: PropTypes.object,
  setState: PropTypes.func,
  supportedDomains: PropTypes.array,
}

const mapStateToProps = state => ({
  redirect: state.app.pages.newRecipePage.forms.recipeAdded,
  supportedDomains: state.app.pages.newRecipePage.supportedDomains,
})

const mapDispatchToProps = (dispatch) =>
  bindActionCreators(
    {
      submitForm: submitForm,
      setState: setState,
    },
    dispatch,
  )

export const NewRecipePageContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(NewRecipePage)
