import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { Link } from 'react-router-dom'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { Ingredient } from 'app-components/Ingredient'

export const CartPage = ({
  cart,
}) => {
  const ingredients = cart.items
    ? cart.items.map(ingredient => (
      <Grid item key={ingredient.id} sm={12}>
        <Ingredient ingredient={ingredient.name} unit={ingredient.unit} quantity={ingredient.quantity}/>
      </Grid>
    ))
    : []

  const recipes = cart.recipes
    ? cart.recipes.map(recipe => (
      <Grid item key={recipe.id} sm={12}>
        <Link to={`/recipes/${recipe.id}`}>{recipe.name}</Link>
      </Grid>
    ))
    : []

  return (
    <Grid container spacing={24}>
      <Grid item xs={12} name='title'>
        <Typography variant='headline'>
          {cart.createdAt}
        </Typography>
      </Grid>
      <Grid item xs={6}>
        <Typography variant='title' gutterBottom>Ingredients</Typography>
        {ingredients}
      </Grid>
      <Grid item xs={6}>
        <Typography variant='title' gutterBottom>Recipes</Typography>
        {recipes}
      </Grid>
    </Grid>
  )
}

CartPage.propTypes = {
  cart: PropTypes.object,
}

const mapStateToProps = state => {
  return {
    cart: state.cart
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({
  }, dispatch)
}

export const CartPageContainer = connect(mapStateToProps, mapDispatchToProps)(CartPage)