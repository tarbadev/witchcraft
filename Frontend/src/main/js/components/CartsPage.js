import React from 'react'
import PropTypes from 'prop-types'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'

export const CartsPage = ({history}) => {
  const onNewCartClick = () => {
    history.push('/carts/new')
  }

  return (
    <Grid container spacing={24}>
      <Grid item>
        <Button variant='contained' className='cart-page__new-cart-button' color='primary' onClick={onNewCartClick}>
          New Cart
        </Button>
      </Grid>
    </Grid>
  )
}

CartsPage.propTypes = {
  history: PropTypes.object,
}
