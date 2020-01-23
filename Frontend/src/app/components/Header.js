import React, { useState } from 'react'

import './Header.css'
import PropTypes from 'prop-types'
import { useAppContext } from './StoreProvider'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import IconButton from '@material-ui/core/IconButton'
import Typography from '@material-ui/core/Typography'
import MenuIcon from '@material-ui/icons/Menu'
import DashboardIcon from '@material-ui/icons/Dashboard'
import CalendarTodayIcon from '@material-ui/icons/CalendarToday'
import KitchenIcon from '@material-ui/icons/Kitchen'
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart'
import { LeftDrawer } from 'src/app/components/LeftDrawer'
import { makeStyles, useMediaQuery, useTheme } from '@material-ui/core'

export const DASHBOARD = 'Dashboard'
export const RECIPE = 'Recipes'
export const WEEK = 'Week'
export const CART = 'Carts'

export const Header = () => {
  const { currentHeader } = useAppContext()
  const [isDrawerOpen, setDrawerOpen] = useState(false)
  const links = [
    {
      label: DASHBOARD,
      url: '/',
      icon: <DashboardIcon />,
    },
    {
      label: RECIPE,
      url: '/recipes',
      icon: <KitchenIcon />,
    },
    {
      label: WEEK,
      url: '/weeks',
      icon: <CalendarTodayIcon />,
    },
    {
      label: CART,
      url: '/carts',
      icon: <ShoppingCartIcon />,
    },
  ]

  return <HeaderDisplay
    title={currentHeader}
    links={links.map(link => ({ ...link, current: link.label === currentHeader }))}
    isDrawerOpen={isDrawerOpen}
    handleDrawerToggle={() => setDrawerOpen(!isDrawerOpen)}
    closeDrawer={() => setDrawerOpen(false)}
  />
}

export const DRAWER_WIDTH = 240
const useStyles = makeStyles(theme => ({
  drawerPaper: {
    width: DRAWER_WIDTH,
  },
  appBar: {
    [theme.breakpoints.up('md')]: {
      marginLeft: DRAWER_WIDTH,
      width: `calc(100% - ${DRAWER_WIDTH}px)`,
    },
  },
  menuIcon: {
    [theme.breakpoints.up('md')]: {
      display: 'none',
    },
  },
}))

const HeaderDisplay = ({ title, links, isDrawerOpen, handleDrawerToggle, closeDrawer }) => {
  const classes = useStyles()
  const theme = useTheme()
  const drawerVariant = useMediaQuery(theme.breakpoints.up('md')) ? 'permanent' : 'temporary'

  return (
    <nav>
      <AppBar position='fixed' className={classes.appBar}>
        <Toolbar>
          <IconButton
            color='inherit'
            aria-label='open drawer'
            edge='start'
            onClick={handleDrawerToggle}
            className={classes.menuIcon}
          >
            <MenuIcon />
          </IconButton>
          <Typography component='h1' variant='h6'>
            {title}
          </Typography>
        </Toolbar>
      </AppBar>
      <LeftDrawer
        open={isDrawerOpen}
        closeDrawer={closeDrawer}
        links={links}
        variant={drawerVariant}
        paperClassName={classes.drawerPaper}
      />
    </nav>
  )
}

HeaderDisplay.propTypes = {
  title: PropTypes.string,
  links: PropTypes.array,
  isDrawerOpen: PropTypes.bool,
  handleDrawerToggle: PropTypes.func,
  closeDrawer: PropTypes.func,
}