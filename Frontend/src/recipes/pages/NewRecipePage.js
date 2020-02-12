import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Button, Grid, makeStyles, Paper, TextField, Typography } from '@material-ui/core'
import { getSupportedDomains, submitForm } from 'src/recipes/actions/NewRecipeActions'

import './NewRecipePage.css'
import GridList from '@material-ui/core/GridList'
import GridListTile from '@material-ui/core/GridListTile'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { RECIPE } from 'src/app/components/Header'
import { initialState } from 'src/app/RootReducer'
import CircularProgress from '@material-ui/core/CircularProgress'

const useStyles = makeStyles({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: '.5em 1em !important',
  },
  buttonProgress: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    marginTop: -12,
    marginLeft: -12,
  },
})

export const NewRecipePageContainer = ({
  history,
}) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()

  useEffect(() => setHeaderConfig({ ...initialState.headerConfig, currentLink: RECIPE, title: 'New Recipe' }), [])

  const useSupportedDomains = () => {
    const [supportedDomains, setSupportedDomains] = useState(state.pages.newRecipePage.supportedDomains)

    useEffect(() => {
      dispatch(getSupportedDomains(newSupportedDomains => setSupportedDomains(newSupportedDomains)))
    }, [])

    return supportedDomains
  }

  const submitFormWithCallback = (url, body, onSuccess) => {
    return dispatch(submitForm(url, body, () => {
      onSuccess()
      history.push('/recipes')
    }))
  }

  return <NewRecipePage
    supportedDomains={useSupportedDomains()}
    submitForm={submitFormWithCallback}
  />
}

NewRecipePageContainer.propTypes = {
  history: PropTypes.object,
}

export const NewRecipePage = ({
  submitForm,
  supportedDomains = [],
}) => {
  const [urlFormValue, setUrlFormValue] = useState('')
  const [manualFormName, setManualFormName] = useState('')
  const [manualFormUrl, setManualFormUrl] = useState('')
  const [manualFormImageUrl, setManualFormImageUrl] = useState('')
  const [manualFormIngredients, setManualFormIngredients] = useState('')
  const [manualFormSteps, setManualFormSteps] = useState('')
  const [manualFormPortions, setManualFormPortions] = useState('')
  const [isAutoImportLoading, setIsAutoImportLoading] = useState(false)
  const classes = useStyles()
  const onUrlFormSubmit = () => {
    setIsAutoImportLoading(true)
    submitForm('/api/recipes/import-from-url', { url: urlFormValue }, () => setIsAutoImportLoading(false))
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
        <Typography variant='h4' className='witchcraft-title'>New Recipe</Typography>
      </Grid>
      <Grid item xs={12} sm={6}>
        <Paper className={classes.paper}>
          <Grid container spacing={3} justify='flex-start' alignItems='flex-start'>
            <Grid item xs={12}>
              <Typography variant='h6' className='witchcraft-title'>Add manually</Typography>
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
        </Paper>
      </Grid>
      <Grid item xs={12} sm={6}>
        <Paper className={classes.paper}>
          <Grid container spacing={3} justify='flex-start' alignItems='flex-start'>
            <Grid item xs={12}>
              <Typography variant='h6' className='witchcraft-title'>Add from URL</Typography>
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
                data-auto-url-submit-button
                color='primary'
                href=''
                disabled={isAutoImportLoading}
                onClick={onUrlFormSubmit}
                style={{ position: 'relative' }}
              >
                Submit
                {isAutoImportLoading && <CircularProgress data-auto-url-loading size={24} className={classes.buttonProgress} />}
              </Button>
            </Grid>
          </Grid>
        </Paper>
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
  history: PropTypes.object,
  supportedDomains: PropTypes.array,
}