import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'

export const CartsPage = ({
  history,
  carts,
}) => {
  const onNewCartClick = () => {
    history.push('/carts/new')
  }

  const cartList = carts.map(cart => (
    <Grid item xs={12} key={cart.id}>
      <Link to={`/carts/${cart.id}`}>{cart.createdAt}</Link>
    </Grid>
  ))

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Button variant='contained' className='cart-page__new-cart-button' color='primary' onClick={onNewCartClick}>
          New Cart
        </Button>
      </Grid>
      {cartList}
    </Grid>
  )
}

CartsPage.propTypes = {
  history: PropTypes.object,
  carts: PropTypes.array,
}

const mapStateToProps = state => {
  return {
    carts: state.carts,
  }
}

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({}, dispatch)
}

export const CartsPageContainer = connect(mapStateToProps, mapDispatchToProps)(CartsPage)
