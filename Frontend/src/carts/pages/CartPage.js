import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'
import Grid from '@material-ui/core/Grid'
import Typography from '@material-ui/core/Typography'

import { IngredientDisplay } from 'src/recipes/components/Ingredient'
import { getCartTitle, getShortCartTitle } from './CartHelper'
import { RecipeCard } from 'src/recipes/components/RecipeCard'
import { deleteCart, findWithAttr, getCart, toggleItem } from 'src/carts/actions/CartActions'

import './CartPage.css'
import { PageTitle } from 'src/app/components/PageTitle'
import { useAppContext } from 'src/app/components/StoreProvider'
import { CART } from 'src/app/components/Header'
import DeleteIcon from '@material-ui/icons/Delete'
import { initialState } from 'src/app/RootReducer'
import MenuItem from '@material-ui/core/MenuItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'

export const CartPageContainer = ({ match, history }) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  const cartId = match.params.id
  const [cart, setCart] = useState(state.cart)
  const onDeleteComplete = () => history.push('/carts')

  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: CART,
    title: getShortCartTitle(cart.createdAt),
    menuList: [
      <MenuItem
        key='menu-delete-cart'
        data-delete-cart-button
        onClick={() => dispatch(deleteCart(cartId, onDeleteComplete, onDeleteComplete))}
      >
        <ListItemIcon>
          <DeleteIcon />
        </ListItemIcon>
        <ListItemText primary="Delete Cart" />
      </MenuItem>,
    ],
  }), [cartId, cart])
  useEffect(() => dispatch(getCart(cartId, data => setCart(data))), [cartId])

  const onToggleItemSuccess = newItem => {
    const newCart = { ...cart }

    newCart.items[findWithAttr(cart.items, 'id', newItem.id)] = newItem

    setCart(newCart)
  }

  const onItemClick = (cartId, itemId, enabled) =>
    dispatch(toggleItem(cartId, itemId, enabled, onToggleItemSuccess))

  return <CartPage cart={cart} onItemClick={onItemClick} title={getCartTitle(cart.createdAt)} />
}

CartPageContainer.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
}

export const CartPage = ({
  onItemClick,
  cart,
  title,
}) => {
  const ingredients = cart.items
    ? cart.items.map(ingredient => (
      <Grid
        item
        key={ingredient.id}
        xs={12}
        data-item
        className={`cart-page__ingredient ${ingredient.enabled ? '' : 'cart-page__ingredient-disabled'}`}
        onClick={() => onItemClick(cart.id, ingredient.id, !ingredient.enabled)}>
        <IngredientDisplay name={ingredient.name} quantity={`${ingredient.quantity} ${ingredient.unit}`} />
      </Grid>
    ))
    : []

  const recipes = cart.recipes
    ? cart.recipes.map(recipe => (
      <Grid item key={recipe.id} xs={12} sm={6}>
        <Link to={`/recipes/${recipe.id}`} data-recipe>
          <RecipeCard title={recipe.name} imgUrl={recipe.imgUrl} />
        </Link>
      </Grid>
    ))
    : []

  return (
    <Grid container spacing={3}>
      <PageTitle title={title} />
      <Grid item xs={12} container name='title'>
        <Typography variant='h5' className='witchcraft-title'>
          {title}
        </Typography>
      </Grid>
      <Grid item xs={12} sm={6}>
        <Grid container spacing={1}>
          <Typography variant='h6' className='witchcraft-title' gutterBottom>Ingredients</Typography>
          {ingredients}
        </Grid>
      </Grid>
      <Grid item xs={12} sm={6}>
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
  title: PropTypes.string,
}
