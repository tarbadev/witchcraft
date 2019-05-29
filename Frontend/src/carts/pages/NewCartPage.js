import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import Switch from '@material-ui/core/Switch'
import FormGroup from '@material-ui/core/FormGroup'
import FormControlLabel from '@material-ui/core/FormControlLabel'

import { setState } from 'src/RootReducer'
import { createCart } from 'src/carts/actions/NewCartActions'

export const NewCartPage = ({
  form,
  toggleRecipeSwitch,
  generateCart,
}) => {
  const recipeList = form.map((recipe, index) => (
    <FormControlLabel
      key={recipe.id}
      control={
        <Switch
          className={`new-cart-page__switch-recipe-${index}`}
          onChange={() => toggleRecipeSwitch(`newCartPage.form.${index}.selected`, !recipe.selected)}
          value={`${recipe.id}`}
          checked={recipe.selected}
          color='primary'
        />
      }
      label={recipe.name}
    />
  ))

  const onGenerateCartClick = () => {
    const recipeIds = form.filter(recipe => recipe.selected).map(recipe => ({ id: recipe.id }))
    generateCart(recipeIds)
  }

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <FormGroup>
          {recipeList}
        </FormGroup>
      </Grid>
      <Grid item xs={12}>
        <Button
          variant='contained'
          color='primary'
          className='new-cart-page__generate-button'
          onClick={onGenerateCartClick}>
          Generate
        </Button>
      </Grid>
    </Grid>
  )
}

NewCartPage.propTypes = {
  form: PropTypes.array,
  toggleRecipeSwitch: PropTypes.func,
  generateCart: PropTypes.func,
}

const mapStateToProps = state => {
  return {
    form: state.newCartPage.form,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
    toggleRecipeSwitch: setState,
    generateCart: createCart,
  }, dispatch)
}

export const NewCartPageContainer = connect(mapStateToProps, mapDispatchToProps)(NewCartPage)
