import React from 'react'
import { Link } from 'react-router-dom'
import Toolbar from '@material-ui/core/Toolbar'
import Button from '@material-ui/core/Button'

import './Header.css'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

export const Header = ({ currentPage }) => {
  const links = [
    {
      label: 'Home',
      url: '/',
    },
    {
      label: 'Recipes',
      url: '/recipes',
    },
    {
      label: 'Weeks',
      url: '/weeks',
    },
    {
      label: 'Carts',
      url: '/carts',
    },
  ]

  const isCurrent = label => label === currentPage

  return (
    <Toolbar disableGutters={true}>
      {
        links.map((link, index) =>
          <Button
            key={`header-link-${index}`}
            component={Link}
            className={`header__link ${isCurrent(link.label) ? 'header__selected' : ''}`}
            color={'default'}
            href={link.url}
            to={link.url}>
            {link.label}
          </Button>,
        )
      }
    </Toolbar>
  )
}

Header.propTypes = {
  currentPage: PropTypes.string,
}

const mapStateToProps = state => {
  return {
    currentPage: state.app.currentPage,
  }
}

export const HeaderContainer = connect(mapStateToProps)(Header)