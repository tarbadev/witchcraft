import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'

import './CartsPage.css'
import { Paper, Typography } from '@material-ui/core'
import { getCartTitle } from './CartHelper'
import { useAppContext } from 'src/app/components/StoreProvider'
import { getAllCarts } from '../actions/CartsActions'
import { CARTS } from 'src/app/components/Header'

export const CartsPageContainer = ({ history }) => {
  const { state, dispatch, setCurrentHeader } = useAppContext()
  setCurrentHeader(CARTS)

  const [carts, setCarts] = useState(state.carts)

  useEffect(() => dispatch(getAllCarts(allCarts => setCarts(allCarts))), [])

  return <CartsPage carts={carts} onNewCartClick={() => history.push('/carts/new')} />
}

CartsPageContainer.propTypes = {
  history: PropTypes.object,
}

export const CartsPage = ({
  onNewCartClick,
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
      <Grid item xs={12}>
        <Button variant='contained' href='' className='cart-page__new-cart-button' color='primary' onClick={onNewCartClick}>
          New Cart
        </Button>
      </Grid>
      <Grid item container xs={12} className='cart-list' spacing={1}>
        {cartList}
      </Grid>
    </Grid>
  )
}

CartsPage.propTypes = {
  onNewCartClick: PropTypes.func,
  carts: PropTypes.array,
}