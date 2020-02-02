import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import { Link } from 'react-router-dom'

import './CartsPage.css'
import { Paper, Typography } from '@material-ui/core'
import { getCartTitle } from './CartHelper'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getAllCarts } from '../actions/CartsActions'
import { CART } from 'src/app/components/Header'
import { initialState } from 'src/app/RootReducer'
import MenuItem from '@material-ui/core/MenuItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import AddIcon from '@material-ui/icons/Add'

export const CartsPageContainer = ({ history }) => {
  const { state, dispatch, setHeaderConfig } = useAppContext()
  useEffect(() => setHeaderConfig({
    ...initialState.headerConfig,
    currentLink: CART,
    title: CART,
    menuList: [
      <MenuItem
        key='menu-new-cart'
        data-new-cart-button
        onClick={() => history.push('/carts/new')}
      >
        <ListItemIcon>
          <AddIcon />
        </ListItemIcon>
        <ListItemText primary="New Cart" />
      </MenuItem>,
    ],
  }), [])

  const [carts, setCarts] = useState(state.carts)

  useEffect(() => dispatch(getAllCarts(allCarts => setCarts(allCarts))), [])

  return <CartsPage carts={carts} onNewCartClick={() => history.push('/carts/new')} />
}

CartsPageContainer.propTypes = {
  history: PropTypes.object,
}

export const CartsPage = ({
  carts,
}) => {
  const cartList = carts.map(cart => (
    <Grid item xs={12} key={cart.id}>
      <Link to={`/carts/${cart.id}`}>
        <Paper className='cart-list__item witchcraft-paper'>
          <Typography className='witchcraft-title'>
            {getCartTitle(cart.createdAt)}
          </Typography>
        </Paper>
      </Link>
    </Grid>
  ))

  return (
    <Grid container spacing={1}>
      {cartList}
    </Grid>
  )
}

CartsPage.propTypes = {
  carts: PropTypes.array,
}