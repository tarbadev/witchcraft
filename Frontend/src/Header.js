import React from 'react'
import { Link } from 'react-router-dom'
import Toolbar from '@material-ui/core/Toolbar'
import Button from '@material-ui/core/Button'

import './Header.css'
import PropTypes from 'prop-types'
import { useAppContext } from './StoreProvider'

export const HOME = 'Home'
export const RECIPES = 'Recipes'
export const WEEKS = 'Weeks'
export const CARTS = 'Carts'

export const HeaderContainer = () => {
  const { currentHeader } = useAppContext()
  return <Header currentPage={currentHeader} />
}

export const Header = ({ currentPage }) => {
  const links = [
    {
      label: HOME,
      url: '/',
    },
    {
      label: RECIPES,
      url: '/recipes',
    },
    {
      label: WEEKS,
      url: '/weeks',
    },
    {
      label: CARTS,
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