import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { Link } from 'react-router-dom'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { Ingredient } from 'src/recipes/components/Ingredient'
import { getCartTitle } from './CartHelper'
import { RecipeCard } from '../../recipes/components/RecipeCard'

export const CartPage = ({
  cart,
}) => {
  const ingredients = cart.items
    ? cart.items.map(ingredient => (
      <Grid item key={ingredient.id} sm={12} data-item>
        <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity} />
      </Grid>
    ))
    : []

  const recipes = cart.recipes
    ? cart.recipes.map(recipe => (
      <Grid item key={recipe.id} sm={6}>
        <Link to={`/recipes/${recipe.id}`} data-recipe>
          <RecipeCard title={recipe.name} imgUrl={recipe.imgUrl} />
        </Link>
      </Grid>
    ))
    : []

  return (
    <Grid container spacing={3}>
      <Grid item xs={12} name='title'>
        <Typography variant='h5' className='witchcraft-title'>
          {getCartTitle(cart.createdAt)}
        </Typography>
      </Grid>
      <Grid item xs={6} container spacing={1}>
        <Typography variant='h6' className='witchcraft-title' gutterBottom>Ingredients</Typography>
        {ingredients}
      </Grid>
      <Grid item xs={6}>
        <Typography variant='h6' className='witchcraft-title' gutterBottom>Recipes</Typography>
        <Grid container spacing={1}>
          {recipes}
        </Grid>
      </Grid>
    </Grid>
  )
}

CartPage.propTypes = {
  cart: PropTypes.object,
}

const mapStateToProps = state => {
  return {
    cart: state.cart,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({}, dispatch)
}

export const CartPageContainer = connect(mapStateToProps, mapDispatchToProps)(CartPage)
