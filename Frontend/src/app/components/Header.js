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
import MoreIcon from '@material-ui/icons/MoreVert'
import { LeftDrawer } from 'src/app/components/LeftDrawer'
import { makeStyles, useMediaQuery, useTheme } from '@material-ui/core'
import Menu from '@material-ui/core/Menu'
import Breadcrumbs from '@material-ui/core/Breadcrumbs'
import Link from '@material-ui/core/Link'
import { Link as RouterLink } from 'react-router-dom'

export const DASHBOARD = 'Dashboard'
export const RECIPE = 'Recipes'
export const WEEK = 'Week'
export const CART = 'Carts'

export const Header = () => {
  const { headerConfig } = useAppContext()
  const [isDrawerOpen, setDrawerOpen] = useState(false)
  const [anchorEl, setAnchorEl] = useState(null)
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

  const breadcrumbLink = links.find(link => link.label === headerConfig.title)
    ? undefined
    : links.find(link => link.label === headerConfig.currentLink)

  return <HeaderDisplay
    title={headerConfig.title}
    links={links.map(link => ({ ...link, current: link.label === headerConfig.currentLink }))}
    isDrawerOpen={isDrawerOpen}
    handleDrawerToggle={() => setDrawerOpen(!isDrawerOpen)}
    closeDrawer={() => setDrawerOpen(false)}
    openDrawer={() => setDrawerOpen(true)}
    anchorEl={anchorEl}
    closeMenu={() => setAnchorEl(null)}
    toggleMenu={({ currentTarget }) => setAnchorEl(anchorEl === null ? currentTarget : null)}
    menus={headerConfig.menuList}
    breadcrumbLink={breadcrumbLink}
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

const HeaderDisplay = ({
  title,
  links,
  isDrawerOpen,
  handleDrawerToggle,
  closeDrawer,
  openDrawer,
  anchorEl,
  closeMenu,
  toggleMenu,
  menus,
  breadcrumbLink,
}) => {
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
          <Breadcrumbs aria-label="breadcrumb" style={{ flexGrow: 1, color: 'white' }}>
            {breadcrumbLink && <Link style={{ color: 'inherit' }} component={RouterLink} to={breadcrumbLink.url}>{breadcrumbLink.label}</Link>}
            <Typography>{title}</Typography>
          </Breadcrumbs>
          <IconButton
            color='inherit'
            aria-label='open menu'
            edge='end'
            onClick={toggleMenu}
            style={{ display: menus.length === 0 ? 'none' : 'inherit' }}
            data-open-menu
          >
            <MoreIcon />
          </IconButton>
          <Menu
            keepMounted
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={closeMenu}
            onClick={toggleMenu}
          >
            {menus}
          </Menu>
        </Toolbar>
      </AppBar>
      <LeftDrawer
        open={isDrawerOpen}
        openDrawer={openDrawer}
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
  openDrawer: PropTypes.func,
  anchorEl: PropTypes.object,
  closeMenu: PropTypes.func,
  toggleMenu: PropTypes.func,
  menus: PropTypes.array,
  breadcrumbLink: PropTypes.object,
}