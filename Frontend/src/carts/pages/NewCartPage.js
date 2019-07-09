import React, { useState } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import Switch from '@material-ui/core/Switch'
import FormGroup from '@material-ui/core/FormGroup'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import { createCart } from 'src/carts/actions/NewCartActions'
import { PageTitle } from '../../PageTitle'

export const NewCartPage = ({
  recipes,
  generateCart,
}) => {
  const [selectedRecipes, setSelectedRecipes] = useState([])

  const recipeList = recipes.map((recipe, index) => (
    <FormControlLabel
      key={recipe.id}
      control={
        <Switch
          className={`new-cart-page__switch-recipe-${index}`}
          onChange={() => setSelectedRecipes([...selectedRecipes, recipe.id])}
          value={`${recipe.id}`}
          checked={selectedRecipes.indexOf(recipe.id) >= 0}
          color='primary'
        />
      }
      label={recipe.name}
    />
  ))

  return (
    <Grid container spacing={3}>
      <PageTitle title='New Cart' />
      <Grid item xs={12}>
        <FormGroup>
          {recipeList}
        </FormGroup>
      </Grid>
      <Grid item xs={12}>
        <Button
          variant='contained'
          color='primary'
          href=''
          className='new-cart-page__generate-button'
          onClick={() => generateCart(selectedRecipes)}>
          Generate
        </Button>
      </Grid>
    </Grid>
  )
}

NewCartPage.propTypes = {
  recipes: PropTypes.array,
  generateCart: PropTypes.func,
}

const mapStateToProps = state => ({ recipes: state.app.allRecipes })
const mapDispatchToProps = (dispatch) => bindActionCreators({ generateCart: createCart }, dispatch)

export const NewCartPageContainer = connect(mapStateToProps, mapDispatchToProps)(NewCartPage)
