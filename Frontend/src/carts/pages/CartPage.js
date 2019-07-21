import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { Ingredient } from 'src/recipes/components/Ingredient'
import { getCartTitle } from './CartHelper'
import { RecipeCard } from 'src/recipes/components/RecipeCard'
import { findWithAttr, getCart, toggleItem } from 'src/carts/actions/CartActions'

import './CartPage.css'
import { PageTitle } from 'src/PageTitle'
import { useAppContext } from 'src/StoreProvider'
import { CARTS } from 'src/Header'

export const CartPageContainer = ({ match }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(CARTS)

  const [cart, setCart] = useState(state.cart)

  useEffect(() => {
    dispatch(getCart(match.params.id, data => setCart(data)))
  }, [])

  const onToggleItemSuccess = newItem => {
    const newCart = {
      ...cart,
    }

    newCart.items[findWithAttr(cart.items, 'id', newItem.id)] = newItem

    setCart(newCart)
  }

  const onItemClick = (cartId, itemId, enabled) =>
    dispatch(toggleItem(cartId, itemId, enabled, onToggleItemSuccess))

  return <CartPage cart={cart} onItemClick={onItemClick} />
}

CartPageContainer.propTypes = {
  match: PropTypes.object,
}

export const CartPage = ({
  onItemClick,
  cart,
}) => {
  const ingredients = cart.items
    ? cart.items.map(ingredient => (
      <Grid
        item
        key={ingredient.id}
        sm={12}
        data-item
        className={`cart-page__ingredient ${ingredient.enabled ? '' : 'cart-page__ingredient-disabled'}`}
        onClick={() => onItemClick(cart.id, ingredient.id, !ingredient.enabled)}>
        <Ingredient
          ingredient={ingredient.name}
          unit={ingredient.unit}
          quantity={ingredient.quantity} />
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

  const title = getCartTitle(cart.createdAt)

  return (
    <Grid container spacing={3}>
      <PageTitle title={title} />
      <Grid item xs={12} name='title'>
        <Typography variant='h5' className='witchcraft-title'>
          {title}
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
  onItemClick: PropTypes.func,
  cart: PropTypes.object,
}
