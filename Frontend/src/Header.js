import React from 'react'
import { Link } from 'react-router-dom'
import Toolbar from '@material-ui/core/Toolbar'
import Button from '@material-ui/core/Button'

import './Header.css'

export const Header = () => {
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

  const currentUrl = window.location.pathname

  const isCurrent = link => {
    if (link.label === 'Home') {
      return currentUrl === '' || currentUrl === '/'
    }

    return currentUrl.startsWith(link.url)
  }

  return (
    <Toolbar disableGutters={true}>
      {
        links.map((link, index) =>
          <Button
            key={`header-link-${index}`}
            component={Link}
            className={`header__link ${isCurrent(link) ? 'header__selected' : ''}`}
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